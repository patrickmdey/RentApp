package ar.edu.itba.paw.models.exceptions;

public class LocationNotFoundException extends NotFoundException {
    @Override
    public String getMessage() {
        return "exception.LocationNotFound";
    }
}
