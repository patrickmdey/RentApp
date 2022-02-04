package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.DBImage;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PictureDTO {
    private URI url;
    private byte[] image;

    public static PictureDTO fromPicture(DBImage dbImage, UriInfo uri) {
        PictureDTO toReturn = new PictureDTO();
        toReturn.url = uri.getBaseUriBuilder().path("images").path(String.valueOf(dbImage.getId())).build();
        return toReturn;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}