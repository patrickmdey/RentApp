package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.annotations.FieldsEquality;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldsEquality(firstFieldName = "password", secondFieldName = "confirmPassword")
public class PasswordForm {

    @NotEmpty
    @Size(min=8, max=100)
    private String password;

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
