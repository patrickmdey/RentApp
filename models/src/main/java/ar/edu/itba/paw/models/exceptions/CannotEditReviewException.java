package ar.edu.itba.paw.models.exceptions;

public class CannotEditReviewException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.CannotEditReview";
    }
}
