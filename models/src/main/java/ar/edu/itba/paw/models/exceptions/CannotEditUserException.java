package ar.edu.itba.paw.models.exceptions;

public class CannotEditUserException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.CannotEditUser";
    }
}
