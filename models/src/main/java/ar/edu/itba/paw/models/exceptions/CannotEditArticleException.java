package ar.edu.itba.paw.models.exceptions;

public class CannotEditArticleException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.CannotEditArticle";
    }
}
