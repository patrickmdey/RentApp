package ar.edu.itba.paw.models.exceptions;

public class ReviewNotFoundException extends NotFoundException {
    @Override
    public String getMessage() {
        return "exception.ReviewNotFound";
    }
}
