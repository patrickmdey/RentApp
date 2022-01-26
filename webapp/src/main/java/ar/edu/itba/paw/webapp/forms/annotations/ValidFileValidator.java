package ar.edu.itba.paw.webapp.forms.annotations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidFileValidator implements ConstraintValidator<ValidFile, byte[]> {
    @Override
    public void initialize(ValidFile validFile) {
    }

    @Override
    public boolean isValid(byte[] multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile != null && multipartFile.length > 0;
    }
}
