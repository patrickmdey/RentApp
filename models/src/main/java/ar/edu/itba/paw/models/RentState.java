package ar.edu.itba.paw.models;

public enum RentState {
    PENDING(true),
    ACCEPTED(false),
    DECLINED(false);

    private final boolean isPending;

    RentState(boolean isPending) {
        this.isPending = isPending;
    }

    public boolean getIsPending() {
        return isPending;
    }

}
