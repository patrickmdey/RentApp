package ar.edu.itba.paw.webapp.forms.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {GreaterDateValidator.class})
public @interface GreaterDate {

    String message() default "Second date should be greater than first";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String baseField();

    String matchField();

}
