package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.dto.put.EditReviewDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewReviewDTO extends EditReviewDTO {
    private int renterId;
    private int articleId;


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
