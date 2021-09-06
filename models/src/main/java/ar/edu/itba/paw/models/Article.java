package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.List;

public class Article implements ValidableObject {

    private Long id;
    private String title;
    private String description;
    private Float pricePerDay;
    private List<Category> categories;
    private Long idOwner;

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

    public List<String> isValid() {
        List<String> errors = new ArrayList<>();

        if (!Validations.isValid(title))
            errors.add("The title is not valid");

        if(!Validations.isValid(description))
            errors.add("The description is not valid");

        if (!Validations.isValid(pricePerDay) || pricePerDay <= 0)
            errors.add("The price per day is not valid");

        if ( categories.size()  == 0)
            errors.add("The article has no categories");

        return errors;
    }

}
