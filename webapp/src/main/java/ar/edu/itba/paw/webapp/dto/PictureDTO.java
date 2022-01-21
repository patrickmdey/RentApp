package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.DBImage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PictureDTO {
    private URI url;
    private byte[] image;

    public static PictureDTO fromPicture(DBImage dbImage, UriInfo uri){
        PictureDTO toReturn = new PictureDTO();
        //toReturn.url = uri.getAbsolutePathBuilder().path("images").path(String.valueOf(user.getPicture())).build();
        toReturn.image = dbImage.getImg();
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
