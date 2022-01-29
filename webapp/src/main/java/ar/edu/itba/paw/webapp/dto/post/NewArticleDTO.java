package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.dto.put.EditArticleDTO;
import ar.edu.itba.paw.webapp.forms.annotations.ValidFile;
import java.util.List;

public class NewArticleDTO extends EditArticleDTO {

    @ValidFile
    private List<byte[]> images;

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}
