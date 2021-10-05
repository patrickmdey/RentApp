package ar.edu.itba.paw.models.exceptions;

public class UnableToSendEmailException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.UnableToSendEmail";
    }
}
