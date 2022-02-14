package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.PictureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("images")
public class PictureController {

    @Autowired
    private ImageService is;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@QueryParam("fromArticle") int articleId) {
        final List<PictureDTO> pictures = is.listFromArticle(articleId).stream().
                map(image -> PictureDTO.fromPicture(image, uriInfo)).collect(Collectors.toList());

        if (pictures.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<List<PictureDTO>>(pictures) {}).build();
    }

    @GET
    @Path("/{id}")
    @Produces("image/*")
    public Response getById(@PathParam("id") long id, @Context Request request) {
        DBImage img = is.findById(id).orElseThrow(ImageNotFoundException::new);

        EntityTag tag = new EntityTag(String.valueOf(id));
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoTransform(true);
        cacheControl.setMustRevalidate(true);

        Response.ResponseBuilder response = request.evaluatePreconditions(tag);
        if (response == null)
            response = Response.ok(img.getImg()).tag(tag);

        return response.cacheControl(cacheControl).build();
    }
}