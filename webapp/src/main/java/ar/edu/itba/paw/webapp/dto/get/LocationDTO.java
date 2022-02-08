package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.Locations;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class LocationDTO {
    private URI url;
    private String id;
    private String name;

    public static LocationDTO fromLocation(Locations location, UriInfo uri) {
        LocationDTO toReturn = new LocationDTO();
        toReturn.id = location.name();
        toReturn.url = uri.getBaseUriBuilder().path("locations").path(toReturn.id).build();
        toReturn.name = location.getName();
        return toReturn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

}