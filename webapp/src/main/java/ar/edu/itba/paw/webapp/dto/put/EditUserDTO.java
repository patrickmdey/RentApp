package ar.edu.itba.paw.webapp.dto.put;

import ar.edu.itba.paw.models.Locations;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditUserDTO {
    @NotEmpty(message = "NotEmpty.accountForm.firstName")
    @Size(min = 3, max = 20, message = "Size.accountForm.firstName")
    private String firstName;

    @NotEmpty(message = "NotEmpty.accountForm.lastName")
    @Size(min = 3, max = 20, message = "Size.accountForm.lastName")
    private String lastName;

    @NotNull(message = "NotNull.accountForm.location")
    private Locations location;

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

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }
}