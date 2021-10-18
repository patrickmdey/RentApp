package ar.edu.itba.paw.models.exceptions;

public class CannotCreateUserException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.cannotCreateUser";
    }
}
