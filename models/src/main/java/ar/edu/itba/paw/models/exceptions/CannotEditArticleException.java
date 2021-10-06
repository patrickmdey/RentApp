package ar.edu.itba.paw.models.exceptions;

public class CannotEditArticleException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.CannotEditArticle";
    }
}
