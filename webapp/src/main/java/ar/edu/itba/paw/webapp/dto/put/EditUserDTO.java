package ar.edu.itba.paw.webapp.dto.put;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditUserDTO {
    @NotEmpty
    @Size(min = 3, max = 20)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String lastName;

    @NotNull
    private Long location;

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

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }
}
