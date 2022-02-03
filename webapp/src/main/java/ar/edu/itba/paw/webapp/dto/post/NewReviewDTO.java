package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.dto.put.EditReviewDTO;
import javax.validation.constraints.NotNull;

public class NewReviewDTO extends EditReviewDTO {
    @NotNull(message = "NotNull.reviewForm.renterId")
    private Integer renterId;

    @NotNull(message = "NotNull.reviewForm.articleId")
    private Integer articleId;

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
