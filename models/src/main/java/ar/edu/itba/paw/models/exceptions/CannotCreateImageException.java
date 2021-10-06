package ar.edu.itba.paw.models.exceptions;

public class CannotCreateImageException extends RuntimeException {

    @Override
    public String getMessage() {
        return "exception.CannotCreateImage";
    }
}
