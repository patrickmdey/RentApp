package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.Annotations.FutureDate;
import ar.edu.itba.paw.webapp.forms.Annotations.GreaterDate;
import ar.edu.itba.paw.webapp.forms.Annotations.UniqueRentRequest;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;


@GreaterDate(baseField = "startDate", matchField = "endDate")
@UniqueRentRequest(startDate = "startDate", endDate = "endDate", articleId = "articleId", renterId = "renterId")
public class RentProposalForm {

    @NotNull
    @NotEmpty
    @FutureDate
    private String startDate;

    @NotNull
    @NotEmpty
    private String endDate;

    @NotEmpty
    private String message;

    private String articleId;
    private String renterId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }
}
