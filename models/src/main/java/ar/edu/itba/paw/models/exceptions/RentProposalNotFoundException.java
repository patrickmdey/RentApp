package ar.edu.itba.paw.models.exceptions;

public class RentProposalNotFoundException  extends RuntimeException{
    @Override
    public String getMessage() {
        return "exception.RentProposalNotFound";
    }
}
