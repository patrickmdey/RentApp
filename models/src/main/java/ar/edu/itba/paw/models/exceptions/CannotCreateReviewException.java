package ar.edu.itba.paw.models.exceptions;

public class CannotCreateReviewException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.cannotCreateReview";
    }
}
