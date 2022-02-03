package ar.edu.itba.paw.webapp.dto.post;

import ar.edu.itba.paw.webapp.dto.put.EditUserDTO;
import ar.edu.itba.paw.webapp.forms.annotations.FieldsEquality;
import ar.edu.itba.paw.webapp.forms.annotations.UserNotExists;
import ar.edu.itba.paw.webapp.forms.annotations.ValidFile;
import ar.edu.itba.paw.webapp.utils.DtoUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@FieldsEquality(firstFieldName = "password", secondFieldName = "confirmPassword",
        message = "FieldsEquality.accountForm.password")
public class NewUserDTO extends EditUserDTO {
    @NotEmpty(message = "NotEmpty.accountForm.password")
    @Size(min = 8, max = 20, message = "Size.accountForm.password")
    private String password;

    @NotEmpty(message = "NotEmpty.accountForm.confirmPassword")
    private String confirmPassword;

    @Email(message = "Email.accountForm.email")
    @Size(min = 3, max = 320, message = "Size.accountForm.email")
    @UserNotExists(message = "UserNotExists.accountForm.email")
    @NotEmpty(message = "NotEmpty.accountForm.email")
    private String email;

    @ValidFile(message = "ValidFile.accountForm.img")
    private byte[] image;

    @NotNull(message = "NotNull.accountForm.isOwner")
    private Boolean isOwner;

    public static NewUserDTO fromMultipartData(FormDataMultiPart data) {
        NewUserDTO toReturn = new NewUserDTO();
        Map<String, List<FormDataBodyPart>> map = data.getFields();
        toReturn.setFirstName(DtoUtils.getFromMap(map, "firstName"));
        toReturn.setLastName(DtoUtils.getFromMap(map, "lastName"));
        toReturn.setPassword(DtoUtils.getFromMap(map, "password"));
        toReturn.setConfirmPassword(DtoUtils.getFromMap(map, "confirmPassword"));
        toReturn.setEmail(DtoUtils.getFromMap(map, "email"));

        toReturn.setLocation(DtoUtils.getFromMap(map, "location", list ->
                list.get(0).getValueAs(Long.class)));

        toReturn.setIsOwner(DtoUtils.getFromMap(map, "isOwner", (list) ->
                list.get(0).getValueAs(Boolean.class)));

        toReturn.setImage(DtoUtils.getFromMap(map, "image", (list) ->
                list.get(0).getValueAs(byte[].class)));

        return toReturn;
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

    public Boolean isOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean owner) {
        isOwner = owner;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
