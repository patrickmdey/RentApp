package ar.edu.itba.paw.webapp.forms.Annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {UserNotExistsValidator.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNotExists {
    String message() default "An user with that email already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
