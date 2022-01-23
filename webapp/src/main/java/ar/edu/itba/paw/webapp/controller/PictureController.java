package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.webapp.dto.LocationDTO;
import ar.edu.itba.paw.webapp.dto.PictureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Arrays;
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
        Article article = as.findById(articleId).orElse(null);
        if (article == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        final List<PictureDTO> pictures = article.getImages().stream().
                map(image -> PictureDTO.fromPicture(image, uriInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<PictureDTO>>(pictures) {}).build();
    }

    @GET
    @Path("/{id}")
    @Produces("image/*")
    public Response getById(@PathParam("id") long id) {
        DBImage img = is.findById(id).orElse(null);
        if (img == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(img.getImg()).build();
    }
}
