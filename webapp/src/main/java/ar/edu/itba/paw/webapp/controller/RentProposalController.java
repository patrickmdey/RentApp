package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.webapp.dto.RentProposalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
    public Response list(@QueryParam("toUser") int userId, @QueryParam("state") int state,
                         @QueryParam("page") @DefaultValue("1") int page) {
        final List<RentProposalDTO> proposals = rs.ownerRequests(userId, RentState.values()[state], page).stream().
                map(proposal -> RentProposalDTO.fromRentProposal(proposal, uriInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<RentProposalDTO>>(proposals) {}).build();
    }
}
