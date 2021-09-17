package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.Annotations.FieldsEquality;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@FieldsEquality(firstFieldName = "password", secondFieldName = "confirmPassword")
public class AccountForm {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Long location;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @NotNull
    private Boolean isOwner;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean owner) {
        isOwner = owner;
    }
}
