package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.webapp.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    @Context
    private UriInfo uriInfo;

    @GET
    public Response list() {
        List<CategoryDTO> categories = cs.listCategories()
                .stream()
                .map((Category category) -> CategoryDTO.fromCategory(category, uriInfo)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<CategoryDTO>>(categories) {
        }).build();
    }

}
