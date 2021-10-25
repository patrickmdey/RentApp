package ar.edu.itba.paw.models;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_seq")
    @SequenceGenerator(sequenceName = "article_id_seq", name = "article_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 310)
    private String description;

    @Column(name = "price_per_day", nullable = false)
    private Float pricePerDay;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_picture",
            joinColumns=@JoinColumn(name="article_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="picture_id", referencedColumnName="id")
    )
    private Set<DBImage> images = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_category",
            joinColumns=@JoinColumn(name="article_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="category_id", referencedColumnName="id")
    )
    private Set<Category> categories = new HashSet<>();

    //@OneToMany(mappedBy = "article", orphanRemoval = true)
    //private List<Review> reviews = new ArrayList<>();

    @Transient
    private Long timesRented = 0L; //TODO cambiar tipo de dato

    @Transient
    private long rating = 0;

    /*package*/ Article() {
        //Just for Hibernate
    }

    public Article(String title, String description, Float pricePerDay, User owner) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.owner = owner;
    }


    public Article(long id, String title, String description, Float pricePerDay, User owner) {
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.id = id;
        this.owner = owner;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
        return Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(pricePerDay, article.pricePerDay) && Objects.equals(categories, article.categories) && Objects.equals(owner, article.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pricePerDay, categories, owner);
    }

    public void setImages(Set<DBImage> images) {
        this.images = images;
    }

    public void addImage(DBImage img) {
        this.images.add(img);
    }

    public List<DBImage> getImages() {
        return new ArrayList<>(images);
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
