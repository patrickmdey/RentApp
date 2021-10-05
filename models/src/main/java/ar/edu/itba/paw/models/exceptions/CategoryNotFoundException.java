package ar.edu.itba.paw.models.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "exception.CategoryNotFound";
    }
}
