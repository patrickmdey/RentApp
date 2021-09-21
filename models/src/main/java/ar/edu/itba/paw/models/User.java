package ar.edu.itba.paw.models;

public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long location;
    private Long picture;
    private UserType type;

    public User(String email, String password, String firstName, String lastName, Long location, Long picture, int type) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.picture = picture;
        if (type == 0 || type == 1)
            this.type = UserType.values()[type];
        else
            ;//Throw exception
    }

    public User(long id, String email, String password, String firstName, String lastName, Long location, Long picture, int type) {
        this(email, password, firstName, lastName, location, picture, type);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public Long getPicture() {
        return picture;
    }

    public void setPicture(Long picture) {
        this.picture = picture;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + " | Id: " + id;
    }
}
