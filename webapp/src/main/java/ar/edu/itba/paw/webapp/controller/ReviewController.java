package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.dto.get.ReviewDTO;
import ar.edu.itba.paw.webapp.dto.post.NewReviewDTO;
import ar.edu.itba.paw.webapp.dto.put.EditReviewDTO;
import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    private final Logger reviewLogger = LoggerFactory.getLogger(ReviewController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") final long id) {
        final Review review = rs.findById(id).orElseThrow(ReviewNotFoundException::new);
        return Response.ok(ReviewDTO.fromReview(review, uriInfo)).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response list(@NotNull(message = "NotNull.getReviews.articleId") @QueryParam("fromArticle") Integer articleId,
                         @QueryParam("limit") Long limit,
                         @QueryParam("page") @DefaultValue("1") @Min(value = 1, message = "List.minPage") long page) {
        final List<ReviewDTO> reviews = rs.getPaged(articleId, limit, page)
                .stream().map(review -> ReviewDTO.fromReview(review, uriInfo)).collect(Collectors.toList());

        if(reviews.isEmpty())
            return Response.noContent().build();

        final long maxPage = rs.getMaxPage(articleId, limit);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("fromArticle", articleId);

        return ApiUtils.generateResponseWithLinks(Response.ok
                (new GenericEntity<List<ReviewDTO>>(reviews) {}), page, maxPage, uriBuilder);
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @PreAuthorize("@webSecurity.checkCanReview(authentication, #reviewDTO.articleId)")
    public Response createReview(@Valid NewReviewDTO reviewDTO) {
        User user = ApiUtils.retrieveUser(securityContext, us);
        reviewLogger.info("publishing review for article with id '{}' with params --> rating: {}, " +
                "message: {}", reviewDTO.getArticleId(), reviewDTO.getRating(), reviewDTO.getMessage());

        final Review review = rs.create(reviewDTO.getRating(), reviewDTO.getMessage(),
                reviewDTO.getArticleId(), user.getId());

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(review.getId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    @PreAuthorize("@webSecurity.checkIsReviewOwner(authentication, #id)")
    public Response modifyReview(@PathParam("id") long id, @Valid EditReviewDTO reviewDTO) {
        reviewLogger.info("editing review with id '{}' with params --> rating: {}, message: {}",
                id, reviewDTO.getRating(), reviewDTO.getMessage());

        rs.update(reviewDTO.getRating(), reviewDTO.getMessage(), id);
        return Response.ok().build();
    }
}