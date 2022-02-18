package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.utils.JwtTokenUtil;
import ar.edu.itba.paw.webapp.dto.get.UserDTO;
import ar.edu.itba.paw.webapp.dto.post.NewUserDTO;
import ar.edu.itba.paw.webapp.dto.put.EditPasswordDTO;
import ar.edu.itba.paw.webapp.dto.put.EditUserDTO;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("users")
@Component
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private ValidatorFactory validatorFactory;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private ServletContext servletContext;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Inject
    private javax.inject.Provider<ContainerRequest> requestProvider;

    private final Logger userLogger = LoggerFactory.getLogger(UserController.class);

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response create(FormDataMultiPart body) {
        NewUserDTO userDto = ApiUtils.validateBean(NewUserDTO.fromMultipartData(body), validatorFactory);
        final User user = us.register(userDto.getEmail(), userDto.getPassword(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getLocation(), userDto.getImage(), userDto.isOwner(),
                uriInfo.getAbsolutePathBuilder().replacePath(null).build().toString()
                        + servletContext.getContextPath(),
                ApiUtils.resolveLocale(requestProvider.get().getAcceptableLanguages()));
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();

        Response.ResponseBuilder responseBuilder = Response.created(uri);
        addJwtToken(responseBuilder, user);

        userLogger.info("Registering new user --> email: {}, location: {}, type: {}",
                userDto.getEmail(), userDto.getLocation(), userDto.isOwner());

        return responseBuilder.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") final long id) {
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(UserDTO.fromUser(user, uriInfo)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #id)")
    public Response deleteById(@PathParam("id") final long id) {
        userLogger.info("deleting account --> id: {}", id);

        us.delete(id);

        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #id)")
    public Response modify(@PathParam("id") final long id, @Valid EditUserDTO userDTO) {
        userLogger.info("Editing user --> name: {}, lastName: {}, location: {}",
                userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLocation());

        us.update(id, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLocation());
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/password")
    @Consumes(value= {MediaType.APPLICATION_JSON})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #id)")
    public Response changePassword(@PathParam("id") final long id, @Valid EditPasswordDTO passwordDTO) {
        us.updatePassword(id, passwordDTO.getPassword());

        User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        Response.ResponseBuilder responseBuilder = Response.ok();
        addJwtToken(responseBuilder, user);

        return responseBuilder.build();
    }

    private void addJwtToken(Response.ResponseBuilder builder, User user) {
            builder.header("Authorization", "Bearer " + jwtUtil.generateAccessToken(user));
    }
}