package ar.edu.itba.paw.models.exceptions;

public class UserNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.UserNotFound";
    }

}
