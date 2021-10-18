package ar.edu.itba.paw.models.exceptions;

public class CannotEditRequestException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.CannotEditRequest";
    }
}
