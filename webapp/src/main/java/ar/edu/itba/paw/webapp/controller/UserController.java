package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Path("users")
@Component
public class UserController {
    @Autowired
    private UserService us;
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response test() {
        return Response.ok().build();
    }

    @POST
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA,})
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response createUser(final UserDTO userDto) {
        InputStream imageStream = userDto.getImage();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[512];
        try {
            while ((nRead = imageStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        byte[] imageData = buffer.toByteArray();

        final User user = us.register(userDto.getEmail(), userDto.getPassword(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getLocation(), imageData, userDto.isOwner(), uriInfo.toString());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getById(@PathParam("id") final long id) {
        final User user = us.findById(id).orElse(null);
        if (user != null) {
            return Response.ok(UserDTO.fromUser(user, uriInfo)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteById(@PathParam("id") final long id) {
        us.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response modify(@PathParam("id") final long id, UserDTO userDTO) {
        us.update(id, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLocation());
        return Response.ok().build();
    }
}