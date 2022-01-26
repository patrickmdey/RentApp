package ar.edu.itba.paw.models.exceptions;

public class ImageNotFoundException extends NotFoundException{
    @Override
    public String getMessage() {
        return "exception.ImageNotFound";
    }
}
