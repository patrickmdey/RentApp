package ar.edu.itba.paw.models.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.ReviewNotFound";
    }
}
