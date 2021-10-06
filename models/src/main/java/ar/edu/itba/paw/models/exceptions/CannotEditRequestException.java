package ar.edu.itba.paw.models.exceptions;

public class CannotEditRequestException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.CannotEditRequest";
    }
}
