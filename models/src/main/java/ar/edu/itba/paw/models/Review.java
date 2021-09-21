package ar.edu.itba.paw.models;

public class Review {
    private long id;
    private int rating;
    private String message;
    private long articleId;
    private long renterId;
    private User renter;

    public Review(long id, int rating, String message, long articleId, long renterId) {
        this(rating, message, articleId, renterId);
        this.id = id;
    }

    public Review(int rating, String message, long articleId, long renterId) {
        this.rating = rating;
        this.message = message;
        this.articleId = articleId;
        this.renterId = renterId;
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
}
