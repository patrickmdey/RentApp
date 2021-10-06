package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.Annotations.FieldsEquality;
import ar.edu.itba.paw.webapp.forms.Annotations.UserNotExists;
import ar.edu.itba.paw.webapp.forms.Annotations.ValidFile;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldsEquality(firstFieldName = "password", secondFieldName = "confirmPassword")
public class AccountForm extends EditAccountForm {

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @NotNull
    @NotEmpty
    @Email
    @UserNotExists
    private String email;

    @ValidFile
    private MultipartFile img;

    @NotNull
    private Boolean isOwner;

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean owner) {
        isOwner = owner;
    }
}
