package ar.edu.itba.paw.models.exceptions;

public class CannotCreateUserException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.cannotCreateUser";
    }
}
