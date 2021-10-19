package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Date;

//@Entity
//@Table(name = "review")
public class Review {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_seq")
//    @SequenceGenerator(sequenceName = "review_id_seq", name = "review_id_seq", allocationSize = 1)
    private Long id;

    private int rating;

//    @Column()
    private String message;
    private long articleId;
    private long renterId;
    private Date createdAt;
    private User renter;

//    /*package*/ Review(){
//        //Just for Hibernate
//    }

    public Review(long id, int rating, String message, long articleId, long renterId, Date createdAt) {
        this(rating, message, articleId, renterId, createdAt);
        this.id = id;
    }

    public Review(int rating, String message, long articleId, long renterId, Date createdAt) {
        this.rating = rating;
        this.message = message;
        this.articleId = articleId;
        this.renterId = renterId;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
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

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getRenterId() {
        return renterId;
    }

    public void setRenterId(long renterId) {
        this.renterId = renterId;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
