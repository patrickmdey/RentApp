package ar.edu.itba.paw.models.exceptions;

public class CannotCreateReviewException extends RuntimeException{
    @Override
    public String getMessage(){
        return "exception.cannotCreateReview";
    }
}
