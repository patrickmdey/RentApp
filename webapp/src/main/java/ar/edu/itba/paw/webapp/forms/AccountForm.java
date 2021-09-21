package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.Annotations.FieldsEquality;
import ar.edu.itba.paw.webapp.forms.Annotations.ValidFile;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@FieldsEquality(firstFieldName = "password", secondFieldName = "confirmPassword")
public class AccountForm extends EditAccountForm {

    @ValidFile
    private MultipartFile img;

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

}
