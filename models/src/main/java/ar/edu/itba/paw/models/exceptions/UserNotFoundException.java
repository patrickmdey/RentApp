package ar.edu.itba.paw.models.exceptions;

public class UserNotFoundException extends NotFoundException {
    @Override
    public String getMessage() {
        return "exception.UserNotFound";
    }

}
