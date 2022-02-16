package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.ArticleDTO;
import ar.edu.itba.paw.webapp.dto.post.NewArticleDTO;
import ar.edu.itba.paw.webapp.dto.put.EditArticleDTO;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("articles")
public class ArticleController {

    @Autowired
    private ArticleService as;

    @Autowired
    private UserService us;

    @Autowired
    private ValidatorFactory validatorFactory;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    private final Logger articleLogger = LoggerFactory.getLogger(ArticleController.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@QueryParam("name") @DefaultValue("") String name,
                         @QueryParam("category") Long category,
                         @QueryParam("orderBy") OrderOptions orderBy, @QueryParam("user") Long user,
                         @QueryParam("location") Locations location,
                         @QueryParam("initPrice") Float initPrice,
                         @QueryParam("endPrice") Float endPrice, @QueryParam("renter") Long renter,
                         @QueryParam("limit") Long limit,
                         @QueryParam("page") @DefaultValue("1") long page) {

        final List<ArticleDTO> articles = as.get(name, category, orderBy, user, location, initPrice, endPrice, renter, limit, page).stream().map(article -> ArticleDTO.fromArticle(article, uriInfo)).collect(Collectors.toList());

        if (articles.isEmpty()) return Response.noContent().build();

        final long maxPage = as.getMaxPage(name, category, user, location, initPrice, endPrice, renter, limit);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("name", name);

        if (category != null) uriBuilder.queryParam("category", category);

        if (orderBy != null) uriBuilder.queryParam("orderBy", orderBy);

        if (user != null) uriBuilder.queryParam("user", user);

        if (location != null) uriBuilder.queryParam("location", location);

        if (initPrice != null) uriBuilder.queryParam("initPrice", initPrice);

        if (endPrice != null) uriBuilder.queryParam("endPrice", endPrice);

        if (renter != null) uriBuilder.queryParam("renter", renter);

        return ApiUtils.generateResponseWithLinks(Response.ok
                (new GenericEntity<List<ArticleDTO>>(articles) {}), page, maxPage, uriBuilder);
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    public Response create(FormDataMultiPart body) {
        NewArticleDTO articleDTO = ApiUtils.validateBean(NewArticleDTO.fromMultipartData(body), validatorFactory);
        final Article article = as.createArticle(articleDTO.getTitle(), articleDTO.getDescription(), articleDTO.getPricePerDay(),
                articleDTO.getCategories(), articleDTO.getImages(),
                ApiUtils.retrieveUser(securityContext, us).getId());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(article.getId())).build();

        articleLogger.info("creating article with params --> name: {}, description: {}, price: {}, "
                        + "categories: {}", article.getTitle(), article.getDescription(),
                article.getPricePerDay(), article.getCategories());

        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") final long id) {
        final Article article = as.findById(id).orElseThrow(ArticleNotFoundException::new);
        return Response.ok(ArticleDTO.fromArticle(article, uriInfo)).build();
    }

    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    @PreAuthorize("@webSecurity.checkIsArticleOwner(authentication, #id)")
    public Response modify(@PathParam("id") long id, @Valid EditArticleDTO articleDTO) {
        as.editArticle(id, articleDTO.getTitle(), articleDTO.getDescription(), articleDTO.getPricePerDay(), articleDTO.getCategories());

        articleLogger.info("Editing article post with params --> name: {}, description: {}, price: {}, "
                + "categories: {}", articleDTO.getTitle(), articleDTO.getDescription(),
                articleDTO.getPricePerDay(), articleDTO.getCategories());

        return Response.ok().build();
    }

    @GET
    @Path("/{id}/related")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response related(@PathParam("id") long id) {
        List<ArticleDTO> articles = as.recommendedArticles(id).stream().map((Article article) -> ArticleDTO.fromArticle(article, uriInfo)).collect(Collectors.toList());

        if (articles.isEmpty()) return Response.noContent().build();
        return Response.ok(new GenericEntity<List<ArticleDTO>>(articles) {}).build();
    }
}
