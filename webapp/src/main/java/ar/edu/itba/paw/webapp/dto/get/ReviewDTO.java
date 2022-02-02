package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.Review;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;

public class ReviewDTO {
    private int rating;

    private String message;

    private LocalDate createdAt;

    private long id;

    private URI url;
    private URI articleUrl;
    private URI renterUrl;

    public static ReviewDTO fromReview(Review review, UriInfo uri) {
        ReviewDTO toReturn = new ReviewDTO();
        toReturn.rating = review.getRating();
        toReturn.message = review.getMessage();
        toReturn.id = review.getId();
        toReturn.createdAt = review.getCreatedAt();
        toReturn.url = uri.getBaseUriBuilder().path("reviews").path(String.valueOf(review.getId())).build();
        toReturn.renterUrl = uri.getBaseUriBuilder().path("users").path(String.valueOf(review.getRenter().getId())).build();
        toReturn.articleUrl = uri.getBaseUriBuilder().path("articles").path(String.valueOf(review.getArticle().getId())).build();
        return toReturn;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public URI getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(URI articleUrl) {
        this.articleUrl = articleUrl;
    }

    public URI getRenterUrl() {
        return renterUrl;
    }

    public void setRenterUrl(URI renterUrl) {
        this.renterUrl = renterUrl;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
