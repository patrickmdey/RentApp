package ar.edu.itba.paw.models.exceptions;

public class CannotCreateProposalException extends RuntimeException {
    @Override
    public String getMessage() {
        return "exception.CannotCreateProposal";
    }
}
