package ar.edu.itba.paw.models.exceptions;

public class CannotEditUserException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.CannotEditUser";
    }
}
