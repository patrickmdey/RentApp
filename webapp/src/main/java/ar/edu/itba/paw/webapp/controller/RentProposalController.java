package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.webapp.dto.RentProposalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response list(@QueryParam("toUser") Integer userId, @QueryParam("state") int state,
                         @QueryParam("page") @DefaultValue("1") int page) {

        if (userId == null) { // TODO: manejo de sesion para saber que usuario esta loggeado
            return Response.noContent().build();
        }

        final List<RentProposalDTO> proposals = rs.ownerRequests(userId, RentState.values()[state], page).stream().
                map(proposal -> RentProposalDTO.fromRentProposal(proposal, uriInfo)).collect(Collectors.toList());


        return Response.ok(new GenericEntity<List<RentProposalDTO>>(proposals) {}).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response createProposal(final RentProposalDTO rentProposalDTO) {
        // TODO: en realidad el renterId viene de la sesion
        final RentProposal rentProposal = rs.create(rentProposalDTO.getMessage(), rentProposalDTO.getStartDate(),
                rentProposalDTO.getEndDate(), rentProposalDTO.getArticleId(), rentProposalDTO.getRenterId(), uriInfo.toString());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(rentProposal.getId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response modify(@PathParam("id") long id, RentProposalDTO rentProposalDTO) {
        rs.setRequestState(id, rentProposalDTO.getState(), uriInfo.getAbsolutePath().toString());
        return Response.ok().build();
    }

}
