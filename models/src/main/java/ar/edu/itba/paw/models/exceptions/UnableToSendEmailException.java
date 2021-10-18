package ar.edu.itba.paw.models.exceptions;

public class UnableToSendEmailException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.UnableToSendEmail";
    }
}
