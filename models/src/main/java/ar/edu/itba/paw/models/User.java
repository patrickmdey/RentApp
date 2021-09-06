package ar.edu.itba.paw.models;

public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String location;
    private String photo;
    private UserType type;

    public User(String email, String password, String firstName, String lastName, String location, int type) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        if (type == 0 || type == 1)
            this.type = UserType.values()[type];
        else
            ;//Throw exception
    }

    public User(int id, String email, String password, String firstName, String lastName, String location, int type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        if (type == 0 || type == 1)
            this.type = UserType.values()[type];
        else
            ;//Throw exception
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
