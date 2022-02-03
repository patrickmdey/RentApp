package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.CategoryDTO;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Path("categories")
public class CategoryController {

    @Autowired
    private CategoryService cs;

    @Autowired
    private ArticleService as;

    @Autowired
    private MessageSource messageSource;

    @Inject
    private javax.inject.Provider<ContainerRequest> requestProvider;

    @Context
    private UriInfo uriInfo;


    // TODO: delegar la logica del articleId al servicio
    @GET
    public Response list(@QueryParam("fromArticle") Integer articleId) {
        List<Category> categories; //TODO probar con collection
        if (articleId == null) {
            categories = cs.listCategories();
        } else {
            categories = listCategoriesFromArticle(articleId);
        }

        if (categories.isEmpty())
            return Response.noContent().build();

        List<Locale> languages = requestProvider.get().getAcceptableLanguages();
        Locale lang = languages.isEmpty() ? LocaleContextHolder.getLocale() : languages.get(0);
        List<CategoryDTO> mappedCategories = categories.stream().map((Category category) ->
                CategoryDTO.fromCategory(category, uriInfo, messageSource, lang)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<CategoryDTO>>(mappedCategories) {}).build();
    }

    private List<Category> listCategoriesFromArticle(int articleId) {
        Article article = as.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        return new ArrayList<>(article.getCategories());
    }
}
