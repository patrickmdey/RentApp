package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.forms.annotations.FutureDate;
import ar.edu.itba.paw.webapp.forms.annotations.GreaterDate;
import ar.edu.itba.paw.webapp.forms.annotations.UniqueRentRequest;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@GreaterDate(baseField = "startDate", matchField = "endDate")
@UniqueRentRequest(startDate = "startDate", endDate = "endDate", articleId = "articleId", renterId = "renterId")
public class NewRentProposalDTO {

    @NotNull
    @NotEmpty
    @FutureDate
    private LocalDate startDate;

    @NotNull
    @NotEmpty
    private LocalDate endDate;

    @NotEmpty
    @Size(min=10, max=310)
    private String message;

    @NotNull
    private Integer articleId;

    @NotNull
    private Integer renterId;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }
}
