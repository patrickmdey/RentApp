package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article {

    private long id;
    private String title;
    private String description;
    private Float pricePerDay;
    private List<Category> categories = new ArrayList<>();
    private long idOwner;
    private Locations location;
    private List<Long> images;
    private Long timesRented = 0L;
    private long rating = 0;

    public Article(long id, String title, String description, Float pricePerDay,
                   List<Category> categories, long idOwner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.categories = categories;
        this.idOwner = idOwner;
    }

    public Article(long id, String title, String description, Float pricePerDay, long idOwner) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.id = id;
        this.idOwner = idOwner;
    }

    public Long getTimesRented() {
        return timesRented;
    }

    public void setTimesRented(Long timesRented) {
        this.timesRented = timesRented;
    }

    public long getId() {
        return id;
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

    public Float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Name: " + title + " | price: " + pricePerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(pricePerDay, article.pricePerDay) && Objects.equals(categories, article.categories) && Objects.equals(idOwner, article.idOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pricePerDay, categories, idOwner);
    }

    public void setImages(List<Long> images) {
        this.images = images;
    }

    public List<Long> getImages() {
        return images;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
