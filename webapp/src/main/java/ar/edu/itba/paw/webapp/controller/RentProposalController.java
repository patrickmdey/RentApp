package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.RequestsGetter;
import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.webapp.dto.get.RentProposalDTO;
import ar.edu.itba.paw.webapp.dto.post.NewRentProposalDTO;
import ar.edu.itba.paw.webapp.dto.put.EditRentProposalDTO;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import ar.edu.itba.paw.interfaces.RentMaxPageGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
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

    private final Logger rentLogger = LoggerFactory.getLogger(RentProposalController.class);


    @GET
    @Path("/received")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #userId)")
    public Response listReceived(@NotNull(message = "NotNull.listProposals.userId") @QueryParam("user") Integer userId,
                                 @NotNull(message = "NotNull.proposals.state") @QueryParam("state") RentState state,
                                 @QueryParam("limit") Long limit,
                                 @QueryParam("page") @DefaultValue("1") int page) {
        return listProposals(userId, state, limit, page, rs::ownerRequests, rs::getReceivedMaxPage);
    }

    @GET
    @Path("/sent")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #userId)")
    public Response listSent(@NotNull(message = "NotNull.listProposals.userId") @QueryParam("user") Integer userId,
                             @NotNull(message = "NotNull.proposals.state") @QueryParam("state") RentState state,
                             @QueryParam("limit") Long limit,
                             @QueryParam("page") @DefaultValue("1") int page) {
        return listProposals(userId, state, limit, page, rs::sentRequests, rs::getSentMaxPage);
    }

    private Response listProposals(int userId, RentState state, Long limit, int page, RequestsGetter getter,
                                   RentMaxPageGetter maxPageGetter) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
                .queryParam("user", userId)
                .queryParam("state", state);

        final List<RentProposalDTO> proposals = getter.get(userId, state.ordinal(), limit, page).stream().
                map(proposal -> RentProposalDTO.fromRentProposal(proposal, uriInfo)).collect(Collectors.toList());

        if (proposals.isEmpty())
            return Response.noContent().build();

        final long maxPage = maxPageGetter.apply(userId, state.ordinal(), limit);
        return ApiUtils.generateResponseWithLinks(Response.ok
                (new GenericEntity<List<RentProposalDTO>>(proposals) {}), page, maxPage, uriBuilder);
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@webSecurity.checkIsSameUser(authentication, #rentProposalDTO.renterId) && " +
            "!@webSecurity.checkIsArticleOwner(authentication, #rentProposalDTO.articleId)")
    public Response createProposal(@Valid final NewRentProposalDTO rentProposalDTO) {
        rentLogger.info("creating new rent proposal with params --> message: {}, " +
                        "articleId: {}, renterId: {}", rentProposalDTO.getMessage(),
                        rentProposalDTO.getArticleId(), rentProposalDTO.getRenterId());

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
    public Response modify(@PathParam("id") long id, @Valid final EditRentProposalDTO rentProposalDTO) {
        rs.setRequestState(id, rentProposalDTO.getState(), uriInfo.getAbsolutePath().toString());
        return Response.ok().build();
    }
}