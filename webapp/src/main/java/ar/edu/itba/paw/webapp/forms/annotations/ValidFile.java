package ar.edu.itba.paw.webapp.forms.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ValidFilesValidator.class, ValidFileValidator.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
    String message() default "Input at least one valid file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
