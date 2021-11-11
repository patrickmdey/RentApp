package ar.edu.itba.paw.models;

public enum RentState {
    PENDING(true, false, false),
    ACCEPTED(false, true, false),
    DECLINED(false, false, true);

    private final boolean isPending;
    private final boolean isAccepted;
    private final boolean isDeclined;

    RentState(boolean isPending, boolean isAccepted, boolean isDeclined) {
        this.isPending = isPending;
        this.isAccepted = isAccepted;
        this.isDeclined = isDeclined;
    }

    public boolean getIsPending() {
        return isPending;
    }
    public boolean getIsAccepted() {
        return isAccepted;
    }
    public boolean getIsDeclined() {
        return isDeclined;
    }
}