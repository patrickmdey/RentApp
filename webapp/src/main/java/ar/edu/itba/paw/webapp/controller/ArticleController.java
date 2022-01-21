package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.webapp.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("articles")
public class ArticleController {

    @Autowired
    private ArticleService as;
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response list() {
        final List<ArticleDTO> articles = as.get(null, null, null,
                null, null, null, null, 1)
                .stream().map(article -> ArticleDTO.fromArticle(article, uriInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ArticleDTO>>(articles) {}).build();
    }

    /* TODO: creacion
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response createUser(final ArticleDTO userDto) {
        final User user = us.register(userDto.getEmail(), userDto.getPassword());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }

     */

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getById(@PathParam("id") final long id) {
        final Article article = as.findById(id).orElse(null);
        if (article != null) {
            return Response.ok(ArticleDTO.fromArticle(article, uriInfo)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
