package ar.edu.itba.paw.models;

public enum UserType {
    OWNER(true),
    RENTER(false);

    private final boolean isOwner;

    UserType(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public boolean getIsOwner() {
        return isOwner;
    }
}
