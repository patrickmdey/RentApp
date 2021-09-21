package ar.edu.itba.paw.models;

import java.util.Date;

public class Review {
    private long id;
    private int rating;
    private String message;
    private long articleId;
    private long renterId;
    private Date createdAt;
    private User renter;

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
