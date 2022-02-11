package ar.edu.itba.paw.webapp.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidFilesValidator implements ConstraintValidator<ValidFile, List<byte[]>> {
    @Override
    public void initialize(ValidFile validFile) {}

    @Override
    public boolean isValid(List<byte[]> files, ConstraintValidatorContext constraintValidatorContext) {
        if (files == null)
            return false;

        for (byte[] file: files) {
            if (file.length > 0)
                return true; // return true if at least one of the files is valid
        }

        return false;
    }
}
