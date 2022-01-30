package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.dto.put.EditArticleDTO;
import ar.edu.itba.paw.webapp.forms.annotations.ValidFile;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NewArticleDTO extends EditArticleDTO {

    @ValidFile
    private List<byte[]> images;

    public static NewArticleDTO fromMultipartData(FormDataMultiPart data) {
        // TODO: exception handling
        NewArticleDTO toReturn = new NewArticleDTO();
        Map<String, List<FormDataBodyPart>> map = data.getFields();
        toReturn.setDescription(map.get("description").get(0).getValue());
        toReturn.setTitle(map.get("title").get(0).getValue());
        toReturn.setPricePerDay(map.get("pricePerDay").get(0).getValueAs(Float.class));
        toReturn.setCategories(
                map.get("categories").stream()
                .map(image -> image.getValueAs(Long.class))
                .collect(Collectors.toList())
        );
        toReturn.setImages(
                map.get("images").stream()
                .map(image -> image.getValueAs(byte[].class))
                .collect(Collectors.toList())
        );
        return toReturn;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}
