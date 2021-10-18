package ar.edu.itba.paw.models.exceptions;

public class CategoryNotFoundException extends NotFoundException {
    @Override
    public String getMessage() {
        return "exception.CategoryNotFound";
    }
}
