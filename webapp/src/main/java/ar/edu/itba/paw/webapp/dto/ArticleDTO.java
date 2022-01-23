package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Article;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class ArticleDTO {
    private String title;

    private String description;
    private float pricePerDay;
    private long timesRented;
    private int rating;
    private long timesReviewed;

    private URI url;
    private URI ownerUrl;
    private URI imagesUrl;
    private URI categoriesUrl;

    // Post/Put only params
    private List<Long> categories;
    private List<byte[]> images;
    private Long ownerId;

    public static ArticleDTO fromArticle(Article article, UriInfo uri){
        ArticleDTO toReturn = new ArticleDTO();
        toReturn.title = article.getTitle();
        toReturn.description = article.getDescription();
        toReturn.pricePerDay = article.getPricePerDay();
        toReturn.timesRented = article.getTimesRented();
        toReturn.rating = article.getRating();
        toReturn.timesReviewed = article.getTimesReviewed();

        toReturn.url = uri.getBaseUriBuilder().path("articles").path(String.valueOf(article.getId())).build();
        toReturn.ownerUrl = uri.getBaseUriBuilder().path("users").path(String.valueOf(article.getOwner().getId())).build();
        toReturn.imagesUrl = uri.getBaseUriBuilder().path("images").queryParam("fromArticle", article.getId()).build();
        //toReturn.categoriesUrl = uri.getAbsolutePathBuilder().path("users").path(String.valueOf(article.getOwner().getId())).build();

        return toReturn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public long getTimesRented() {
        return timesRented;
    }

    public void setTimesRented(long timesRented) {
        this.timesRented = timesRented;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getTimesReviewed() {
        return timesReviewed;
    }

    public void setTimesReviewed(long timesReviewed) {
        this.timesReviewed = timesReviewed;
    }

    public URI getOwnerUrl() {
        return ownerUrl;
    }

    public void setOwnerUrl(URI ownerUrl) {
        this.ownerUrl = ownerUrl;
    }

    public URI getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(URI imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public URI getCategoriesUrl() {
        return categoriesUrl;
    }

    public void setCategoriesUrl(URI categoriesUrl) {
        this.categoriesUrl = categoriesUrl;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}
