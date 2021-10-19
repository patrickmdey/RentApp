package ar.edu.itba.paw.models;

import java.util.Date;

public class RentProposal {

    private long id;
    private String message;
    private Integer state;
    private Date startDate;
    private Date endDate;
    private long articleId;
    private long renterId;

    private User renter;
    private User owner;
    private Article article;

    public RentProposal(long id, String message, Integer state, Date startDate, Date endDate, long articleId, long renterId) {
        this(message, state, startDate, endDate, articleId, renterId);
        this.id = id;
    }

    public RentProposal(String message, Integer state, Date startDate, Date endDate, long articleId, long renterId) {
        this.message = message;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.articleId = articleId;
        this.renterId = renterId;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return '\n' + "startDate: " + startDate + " | endDate: " +
                endDate + " | Message: " + message + " | Article id: " + articleId + '\n';
    }
}
