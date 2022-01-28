package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("categories")
public class CategoryController {

    @Autowired
    private CategoryService cs;

    @Autowired
    private ArticleService as;

    @Context
    private UriInfo uriInfo;

    // TODO: map category description to its i18n version
    @GET
    public Response list(@QueryParam("fromArticle") Integer articleId) {
        List<CategoryDTO> categories;
        if (articleId == null) {
            categories = cs.listCategories().stream().map((Category category) ->
                    CategoryDTO.fromCategory(category, uriInfo)).collect(Collectors.toList());
        } else {
            categories = listCategoriesFromArticle(articleId);
        }

        if (categories.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<List<CategoryDTO>>(categories) {}).build();
    }


    private List<CategoryDTO> listCategoriesFromArticle(int articleId) {
        Article article = as.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        return article.getCategories().stream().map((Category category) ->
                CategoryDTO.fromCategory(category, uriInfo)).collect(Collectors.toList());

    }


}
