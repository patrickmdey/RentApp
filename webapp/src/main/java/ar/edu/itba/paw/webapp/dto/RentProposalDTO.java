package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.RentProposal;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;

public class RentProposalDTO {
    private String message;
    private int state;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean seen;

    private URI url;
    private URI renterUrl;
    private URI articleUrl;

    // Post/Put only params
    private Long articleId;
    private Long renterId;

    public static RentProposalDTO fromRentProposal(RentProposal rp, UriInfo uri){
        RentProposalDTO toReturn = new RentProposalDTO();
        toReturn.message = rp.getMessage();
        toReturn.state = rp.getState();
        toReturn.startDate = rp.getStartDate();
        toReturn.endDate = rp.getEndDate();

        toReturn.url = uri.getBaseUriBuilder().path("proposals").path(String.valueOf(rp.getId())).build();
        toReturn.renterUrl = uri.getBaseUriBuilder().path("users").path(String.valueOf(rp.getRenter().getId())).build();
        toReturn.articleUrl = uri.getBaseUriBuilder().path("articles").path(String.valueOf(rp.getArticle().getId())).build();

        return toReturn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public URI getRenterUrl() {
        return renterUrl;
    }

    public void setRenterUrl(URI renterUrl) {
        this.renterUrl = renterUrl;
    }

    public URI getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(URI articleUrl) {
        this.articleUrl = articleUrl;
    }

    public URI getUrl() {
        return url;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
    }

    public void setUrl(URI url) {
        this.url = url;
    }


}
