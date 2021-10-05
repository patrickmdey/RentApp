package ar.edu.itba.paw.models.exceptions;

public class ArticleNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.ArticleNotFound";
    }
}
