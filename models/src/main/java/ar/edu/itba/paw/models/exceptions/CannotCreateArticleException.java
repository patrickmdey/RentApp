package ar.edu.itba.paw.models.exceptions;

public class CannotCreateArticleException extends RuntimeException{
    @Override
    public String getMessage() {
        return "exception.CannotCreateArticle";
    }
}
