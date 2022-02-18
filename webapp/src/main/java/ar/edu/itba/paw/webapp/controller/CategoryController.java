package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.exceptions.CategoryNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.CategoryDTO;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Path("categories")
public class CategoryController {

    @Autowired
    private CategoryService cs;

    @Autowired
    private MessageSource messageSource;

    @Inject
    private javax.inject.Provider<ContainerRequest> requestProvider;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@QueryParam("fromArticle") Integer articleId) {
        List<Category> categories;
        categories = cs.listCategories(articleId);

        if (categories.isEmpty())
            return Response.noContent().build();

        Locale lang = ApiUtils.resolveLocale(requestProvider.get().getAcceptableLanguages());
        List<CategoryDTO> mappedCategories = categories.stream().map((Category category) ->
                CategoryDTO.fromCategory(category, uriInfo, messageSource, lang)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<CategoryDTO>>(mappedCategories) {}).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") final long id) {
        final Category category = cs.findById(id).orElseThrow(CategoryNotFoundException::new);
        List<Locale> languages = requestProvider.get().getAcceptableLanguages();
        Locale lang = languages.isEmpty() ? LocaleContextHolder.getLocale() : languages.get(0);
        return Response.ok(CategoryDTO.fromCategory(category, uriInfo, messageSource, lang)).build();
    }
}
