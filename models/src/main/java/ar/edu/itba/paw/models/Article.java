package ar.edu.itba.paw.models;

public class Article {

    private Integer id;
    private String title;
    private String description;
    private Float pricePerDay;
    private Integer idCategory;
    private Integer idOwner;

    public Article(Integer id, String title,
                   String description, Float pricePerDay,
                   Integer idCategory, Integer idOwner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.idCategory = idCategory;
        this.idOwner = idOwner;
    }

    public Article(String title, String description, Float pricePerDay, Integer idCategory, Integer idOwner) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.idCategory = idCategory;
        this.idOwner = idOwner;
    }

    public Integer getId() {
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

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Integer idOwner) {
        this.idOwner = idOwner;
    }


}
