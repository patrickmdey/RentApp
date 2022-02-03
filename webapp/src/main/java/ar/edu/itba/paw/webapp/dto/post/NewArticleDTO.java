package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.dto.put.EditArticleDTO;
import ar.edu.itba.paw.webapp.forms.annotations.ValidFile;
import ar.edu.itba.paw.webapp.utils.DtoUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NewArticleDTO extends EditArticleDTO {

    @ValidFile(message = "ValidFile.createArticleForm.files")
    private List<byte[]> images;

    public static NewArticleDTO fromMultipartData(FormDataMultiPart data) {
        NewArticleDTO toReturn = new NewArticleDTO();
        Map<String, List<FormDataBodyPart>> map = data.getFields();
        toReturn.setDescription(DtoUtils.getFromMap(map, "description"));
        toReturn.setTitle(DtoUtils.getFromMap(map, "title"));
        toReturn.setPricePerDay(DtoUtils.getFromMap(map, "pricePerDay", (list) ->
                list.get(0).getValueAs(Float.class)));

        toReturn.setCategories(DtoUtils.getFromMap(map, "categories", (list) ->
                list.stream().map(cat -> cat.getValueAs(Long.class)).collect(Collectors.toList())));

        toReturn.setImages(DtoUtils.getFromMap(map, "images", (list) ->
                list.stream().map(image -> image.getValueAs(byte[].class)).collect(Collectors.toList())
        ));
        return toReturn;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}
