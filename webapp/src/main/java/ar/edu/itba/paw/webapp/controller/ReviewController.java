package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.ArticleDTO;
import ar.edu.itba.paw.webapp.dto.ReviewDTO;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("reviews")
@Component
public class ReviewController {

    @Autowired
    private ReviewService rs;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getById(@PathParam("id") final long id) {
        final Review review = rs.findById(id).orElse(null);
        if (review != null) {
            return Response.ok(ReviewDTO.fromReview(review, uriInfo)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response list(@QueryParam("fromArticle") int articleId, @QueryParam("page") @DefaultValue("1") long page) {
        final List<ReviewDTO> reviews = rs.getPaged(articleId, page)
                .stream().map(review -> ReviewDTO.fromReview(review, uriInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ReviewDTO>>(reviews) {
        }).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response createReview(ReviewDTO reviewDTO) {
        final Review review = rs.create(reviewDTO.getRating(), reviewDTO.getMessage(),
                reviewDTO.getArticleId(), reviewDTO.getRenterId()); // TODO: obtener data de las urls
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(review.getId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Path("/{id}")
    public Response modifyReview(@PathParam("id") long id, ReviewDTO reviewDTO) {
        rs.update(reviewDTO.getRating(), reviewDTO.getMessage(), id);
        return Response.ok().build();
    }
}
