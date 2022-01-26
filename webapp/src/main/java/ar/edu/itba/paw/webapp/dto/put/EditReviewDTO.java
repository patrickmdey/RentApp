package ar.edu.itba.paw.webapp.dto.put;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditReviewDTO {
    @NotEmpty
    @Size(min = 10, max = 310)
    private String message;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
