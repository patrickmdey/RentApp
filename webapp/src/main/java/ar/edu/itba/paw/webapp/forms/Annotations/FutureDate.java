package ar.edu.itba.paw.webapp.forms.Annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {FutureDateValidator.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {

    String message() default "Date must be after today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
