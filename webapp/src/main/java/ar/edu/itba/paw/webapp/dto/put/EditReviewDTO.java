package ar.edu.itba.paw.webapp.dto.put;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditReviewDTO {
    @NotEmpty(message = "NotEmpty.reviewForm.message")
    @Size(min = 10, max = 310, message = "Size.reviewForm.message")
    private String message;

    @NotNull(message = "NotNull.reviewForm.rating")
    @Min(value = 1, message = "Min.reviewForm.rating")
    @Max(value = 5, message = "Max.reviewForm.rating")
    private Integer rating;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}