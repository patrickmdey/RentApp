package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.LocationService;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("locations")
public class LocationController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private LocationService locationService;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@QueryParam("used") @DefaultValue("false") boolean used) {
        final List<LocationDTO> locations = locationService.list(used).stream().map(location ->
                LocationDTO.fromLocation(location, uriInfo)).collect(Collectors.toList());

        if (locations.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<List<LocationDTO>>(locations) {}).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") final Locations id, @Context Request request) {
        // TODO: check if etag is actually correct
        EntityTag tag = new EntityTag(id.toString());
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoTransform(true);
        cacheControl.setMustRevalidate(true);

        Response.ResponseBuilder response = request.evaluatePreconditions(tag);
        if (response == null)
            response = Response.ok(LocationDTO.fromLocation(id, uriInfo)).tag(tag);

        return response.cacheControl(cacheControl).build();
    }
}