package ar.edu.itba.paw.models.exceptions;

public class CannotCreateImageException extends InternalErrorException {

    @Override
    public String getMessage() {
        return "exception.CannotCreateImage";
    }
}
