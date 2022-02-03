package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.forms.annotations.FutureDate;
import ar.edu.itba.paw.webapp.forms.annotations.GreaterDate;
import ar.edu.itba.paw.webapp.forms.annotations.UniqueRentRequest;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@GreaterDate(baseField = "startDate", matchField = "endDate", message = "GreaterDate.rentForm.startDate")
@UniqueRentRequest(startDate = "startDate", endDate = "endDate", articleId = "articleId",
        renterId = "renterId", message = "UniqueRentRequest.rentForm.articleId")
public class NewRentProposalDTO {

    @NotNull
    @NotEmpty(message = "NotEmpty.rentForm.startDate")
    @FutureDate(message = "FutureDate.rentForm.startDate")
    private LocalDate startDate; //TODO chequeo que así funciona, podemos usar:
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@JsonFormat(pattern = "MM/dd/yyyy")

    @NotNull
    @NotEmpty(message = "NotEmpty.rentForm.endDate")
    private LocalDate endDate;

    @NotEmpty(message = "NotEmpty.rentForm.message")
    @Size(min=10, max=310, message = "Size.rentForm.message")
    private String message;

    @NotNull(message = "NotNull.rentForm.articleId")
    private Integer articleId;

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
}
