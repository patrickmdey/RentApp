package ar.edu.itba.paw.models;

import java.util.Date;

public class RentProposal {

    private long id;
    private String comment;
    private Boolean approved;
    private Date startDate;
    private Date endDate;
    private Integer idArticle;
    private Integer idRenter;

    public RentProposal(long id, String comment, Boolean approved, Date startDate, Date endDate, Integer idArticle, Integer idRenter) {
        this(comment, approved, startDate, endDate, idArticle, idRenter);
        this.id = id;
    }

    public RentProposal(String comment, Boolean approved, Date startDate, Date endDate, Integer idArticle, Integer idRenter) {
        this.comment = comment;
        this.approved = approved;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idArticle = idArticle;
        this.idRenter = idRenter;
    }

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdRenter() {
        return idRenter;
    }

    public void setIdRenter(Integer idRenter) {
        this.idRenter = idRenter;
    }
}
