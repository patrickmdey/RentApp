package ar.edu.itba.paw.models;

import java.util.List;

public class Article {

    private long id;
    private String title;
    private String description;
    private Float pricePerDay;
    private List<String> categories;
    private long idOwner;
    private String location;

    public Article(long id, String title, String description, Float pricePerDay,
                   List<String> categories, long idOwner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.categories = categories;
        this.idOwner = idOwner;
    }

    public Article(String title, String description, Float pricePerDay, List<String> categories, long idOwner) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.categories = categories;
        this.idOwner = idOwner;
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
