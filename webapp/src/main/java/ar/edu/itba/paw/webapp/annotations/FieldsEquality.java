package ar.edu.itba.paw.webapp.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FieldsEqualityValidator.class })
public @interface FieldsEquality {

    String message() default "{FieldsEquality.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstFieldName();

    String secondFieldName();
}