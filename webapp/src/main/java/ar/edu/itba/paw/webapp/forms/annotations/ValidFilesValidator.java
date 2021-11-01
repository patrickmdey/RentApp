package ar.edu.itba.paw.webapp.forms.annotations;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidFilesValidator implements ConstraintValidator<ValidFile, List<MultipartFile>> {
    @Override
    public void initialize(ValidFile validFile) {}

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext constraintValidatorContext) {
        for (MultipartFile file: files) {
            if (!file.isEmpty())
                return true; // return true if at least one of the files is valid
        }

        return false;
    }
}
