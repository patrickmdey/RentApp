package ar.edu.itba.paw.models.exceptions;

public class CannotEditArticleCategoryException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.CannotEditArticleCategory";
    }
}
