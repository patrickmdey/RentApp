package ar.edu.itba.paw.models.exceptions;

public class CannotEditReviewException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.CannotEditReview";
    }
}
