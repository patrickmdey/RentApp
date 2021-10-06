package ar.edu.itba.paw.models.exceptions;

public class CannotEditArticleCategoryException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.CannotEditArticleCategory";
    }
}
