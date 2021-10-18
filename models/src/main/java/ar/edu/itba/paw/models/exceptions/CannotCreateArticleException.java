package ar.edu.itba.paw.models.exceptions;

public class CannotCreateArticleException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.CannotCreateArticle";
    }
}
