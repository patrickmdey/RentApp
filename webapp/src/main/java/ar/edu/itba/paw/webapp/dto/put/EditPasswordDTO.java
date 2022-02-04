package ar.edu.itba.paw.webapp.dto.put;

import ar.edu.itba.paw.webapp.forms.annotations.FieldsEquality;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldsEquality(firstFieldName = "password", secondFieldName = "confirmPassword",
        message = "FieldsEquality.passwordForm.password")
public class EditPasswordDTO {
    @NotEmpty(message = "NotEmpty.passwordForm.password")
    @Size(min = 8, max = 20, message = "Size.passwordForm.password")
    private String password;

    @NotEmpty(message = "NotEmpty.passwordForm.confirmPassword")
    private String confirmPassword;

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
}