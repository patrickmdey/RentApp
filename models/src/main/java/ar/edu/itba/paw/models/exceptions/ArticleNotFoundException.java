package ar.edu.itba.paw.models.exceptions;

public class ArticleNotFoundException extends NotFoundException {
    @Override
    public String getMessage() {
        return "exception.ArticleNotFound";
    }
}
