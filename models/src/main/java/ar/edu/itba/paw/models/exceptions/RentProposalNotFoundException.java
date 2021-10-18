package ar.edu.itba.paw.models.exceptions;

public class RentProposalNotFoundException extends NotFoundException {
    @Override
    public String getMessage() {
        return "exception.RentProposalNotFound";
    }
}
