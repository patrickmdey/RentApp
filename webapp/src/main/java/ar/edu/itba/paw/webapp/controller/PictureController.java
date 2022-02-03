package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.PictureDTO;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("images")
public class PictureController {

    @Autowired
    private ArticleService as; //TODO cambiar, esto es una prueba

    @Autowired
    private ImageService is;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@QueryParam("fromArticle") int articleId) {
        // is.findFromArticle(articleId)
        Article article = as.findById(articleId).orElseThrow(ArticleNotFoundException::new);

        final List<PictureDTO> pictures = article.getImages().stream().
                map(image -> PictureDTO.fromPicture(image, uriInfo)).collect(Collectors.toList());

        if (pictures.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<List<PictureDTO>>(pictures) {}).build();
    }

    @GET
    @Path("/{id}")
    @Produces("image/*")
    public Response getById(@PathParam("id") long id) {
        DBImage img = is.findById(id).orElseThrow(ImageNotFoundException::new);
        return Response.ok(img.getImg()).cacheControl(CacheControl.valueOf("public, max-age=" +
                ApiUtils.CACHE_MAX_AGE + ", immutable")).expires(Date.from(LocalDate.now().plusYears(1)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).build();
    }
}
