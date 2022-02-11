package ar.edu.itba.paw.webapp.dto.put;

import ar.edu.itba.paw.models.RentState;
import javax.validation.constraints.NotNull;

public class EditRentProposalDTO {

    @NotNull(message = "NotNull.proposals.state")
    private RentState state;

    public RentState getState() {
        return state;
    }

    public void setState(RentState state) {
        this.state = state;
    }
}
