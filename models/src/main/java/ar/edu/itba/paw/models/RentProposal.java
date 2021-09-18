package ar.edu.itba.paw.models;

import java.util.Date;

public class RentProposal {

    private long id;
    private String message;
    private Boolean approved;
    private Date startDate;
    private Date endDate;
    private long articleId;
    private long renterId;
    private User renter;
    private Article article;

    public RentProposal(long id, String message, Boolean approved, Date startDate, Date endDate, long articleId, long renterId) {
        this(message, approved, startDate, endDate, articleId, renterId);
        this.id = id;
    }

    public RentProposal(String message, Boolean approved, Date startDate, Date endDate, long articleId, long renterId) {
        this.message = message;
        this.approved = approved;
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

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
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

    @Override
    public String toString() {
        return '\n' + "startDate: " + startDate + " | endDate: " +
                endDate + " | Message: " + message + " | Article id: " + articleId + '\n';
    }
}
