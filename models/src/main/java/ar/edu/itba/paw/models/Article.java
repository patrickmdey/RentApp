package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article {

    private Long id;
    private String title;
    private String description;
    private Float pricePerDay;
    private List<String> categories;
    private long idOwner;
    private String location;

    public Article(Long id, String title, String description, Float pricePerDay,
                   List<Category> categories, Long idOwner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.categories = categories;
        this.idOwner = idOwner;
    }

    public Article(String title, String description, Float pricePerDay, List<Category> categories, Long idOwner) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.categories = categories;
        this.idOwner = idOwner;
    }

    public Long getId() {
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

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

}
