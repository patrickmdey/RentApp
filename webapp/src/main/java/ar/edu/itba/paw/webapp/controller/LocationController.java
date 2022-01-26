package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.webapp.dto.ArticleDTO;
import ar.edu.itba.paw.webapp.dto.LocationDTO;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("locations")
public class LocationController {

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list() {
        final List<LocationDTO> locations = Arrays.stream(Locations.values()).map(location ->
                LocationDTO.fromLocation(location, uriInfo)).collect(Collectors.toList());

        if (locations.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<List<LocationDTO>>(locations) {}).build();
    }
}
