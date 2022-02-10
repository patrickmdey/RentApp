package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;

public class RentProposalDTO {
    private String message;
    private RentState state;
    private LocalDate startDate;
    private LocalDate endDate;
    private long id;
    private boolean marked;

    private URI url;
    private URI renterUrl;
    private URI articleUrl;

    public static RentProposalDTO fromRentProposal(RentProposal rp, UriInfo uri) {
        RentProposalDTO toReturn = new RentProposalDTO();
        toReturn.message = rp.getMessage();
        toReturn.state = rp.getState();
        toReturn.startDate = rp.getStartDate();
        toReturn.endDate = rp.getEndDate();
        toReturn.id = rp.getId();
        toReturn.marked = rp.isMarked();

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

    public RentState getState() {
        return state;
    }

    public void setState(RentState state) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
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

    public void setUrl(URI url) {
        this.url = url;
    }
}