package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.Locations;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class LocationDTO {
    private URI url;
    private Long id;
    private String name;

    public static LocationDTO fromLocation(Locations location, UriInfo uri){
        LocationDTO toReturn = new LocationDTO();
        toReturn.id = (long) location.ordinal();
        toReturn.url = uri.getBaseUriBuilder().path("locations").path(String.valueOf(toReturn.id)).build();
        toReturn.name = location.getName();
        return toReturn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
