package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.annotations.FutureDate;
import ar.edu.itba.paw.webapp.annotations.GreaterDate;
import ar.edu.itba.paw.webapp.annotations.UniqueRentRequest;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@GreaterDate(baseField = "startDate", matchField = "endDate", message = "GreaterDate.rentForm.startDate")
@UniqueRentRequest(startDate = "startDate", endDate = "endDate", articleId = "articleId",
        renterId = "renterId", message = "UniqueRentRequest.rentForm.articleId")
public class NewRentProposalDTO {

    @NotNull(message = "NotNull.rentForm.startDate")
    @FutureDate(message = "FutureDate.rentForm.startDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "NotNull.rentForm.endDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotEmpty(message = "NotEmpty.rentForm.message")
    @Size(min=10, max=310, message = "Size.rentForm.message")
    private String message;

    @NotNull(message = "NotNull.rentForm.articleId")
    private Integer articleId;

    @NotNull(message = "NotNull.rentForm.articleId")
    private Long renterId;

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

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
    }
}
