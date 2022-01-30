package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.RequestsGetter;
import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.webapp.dto.get.RentProposalDTO;
import ar.edu.itba.paw.webapp.dto.post.NewRentProposalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component
@Path("proposals")
public class RentProposalController {

    @Autowired
    private RentService rs;

    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/received")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #userId)")
    public Response listReceived(@NotNull @QueryParam("user") int userId,
                                 @NotNull @QueryParam("state") int state,
                                 @QueryParam("page") @DefaultValue("1") int page) {
        return listProposals(userId, state, page, rs::ownerRequests, rs::getReceivedMaxPage);
    }

    @GET
    @Path("/sent")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #userId)")
    public Response listSent(@NotNull @QueryParam("user") int userId,
                             @NotNull @QueryParam("state") int state,
                             @QueryParam("page") @DefaultValue("1") int page) {
        return listProposals(userId, state, page, rs::sentRequests, rs::getSentMaxPage);
    }

    private Response listProposals(int userId, int state, int page,
                                  RequestsGetter getter, BiFunction<Integer, Integer, Long> maxPageGetter) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
                .queryParam("user", userId)
                .queryParam("state", state);

        final List<RentProposalDTO> proposals = getter.get(userId, state, page).stream().
                map(proposal -> RentProposalDTO.fromRentProposal(proposal, uriInfo)).collect(Collectors.toList());

        if (proposals.isEmpty())
            return Response.noContent().build();

        final Long maxPage = maxPageGetter.apply(userId, state);
        return ApiUtils.generateResponseWithLinks(Response.ok
                (new GenericEntity<List<RentProposalDTO>>(proposals) {}), page, maxPage, uriBuilder);
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("isAuthenticated() && " +
            "!@webSecurity.checkIsArticleOwner(authentication, #rentProposalDTO.articleId)")
    public Response createProposal(@Valid final NewRentProposalDTO rentProposalDTO) {
        final RentProposal rentProposal = rs.create(rentProposalDTO.getMessage(),
                rentProposalDTO.getStartDate(), rentProposalDTO.getEndDate(),
                rentProposalDTO.getArticleId(), ApiUtils.retrieveUser(securityContext, us).getId(),
                uriInfo.toString());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(rentProposal.getId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@webSecurity.checkIsRentOwner(authentication, #id)")
    public Response modify(@PathParam("id") long id, @NotNull Integer state) {
        rs.setRequestState(id, state, uriInfo.getAbsolutePath().toString());
        return Response.ok().build();
    }
}