package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_seq")
    @SequenceGenerator(sequenceName = "review_id_seq", name = "review_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 310)
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "renter_id", referencedColumnName = "id")
    private User renter;


    /*package*/ Review() {
        //Just for Hibernate
    }

    public Review(long id, int rating, String message, LocalDate createdAt) {
        this(rating, message, createdAt);
        this.id = id;
    }

    public Review(int rating, String message, LocalDate createdAt) {
        this.rating = rating;
        this.message = message;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
