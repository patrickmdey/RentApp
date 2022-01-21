package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Locations;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class LocationDTO {
    private URI url;
    private String name;

    public static LocationDTO fromLocation(Locations location, UriInfo uri){
        LocationDTO toReturn = new LocationDTO();
        toReturn.url = uri.getBaseUriBuilder().path("locations").path(String.valueOf(location.ordinal())).build();
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
}
