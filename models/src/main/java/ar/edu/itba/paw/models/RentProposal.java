package ar.edu.itba.paw.models;

import java.util.Date;

public class RentProposal {

    private long id;
    private String message;
    private Boolean approved;
    private Date startDate;
    private Date endDate;
    private long idArticle;
    private Integer idRenter;

    public RentProposal(long id, String message, Boolean approved, Date startDate, Date endDate, long idArticle, Integer idRenter) {
        this(message, approved, startDate, endDate, idArticle, idRenter);
        this.id = id;
    }

    public RentProposal(String message, Boolean approved, Date startDate, Date endDate, long idArticle, Integer idRenter) {
        this.message = message;
        this.approved = approved;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idArticle = idArticle;
        this.idRenter = idRenter;
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

    public long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(long idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdRenter() {
        return idRenter;
    }

    public void setIdRenter(Integer idRenter) {
        this.idRenter = idRenter;
    }

    @Override
    public String toString() {
        return '\n' + "startDate: " + startDate + " | endDate: " +
                endDate + " | Message: " + message + " | Article id: " + idArticle + '\n';
    }
}
