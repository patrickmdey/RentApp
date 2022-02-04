package ar.edu.itba.paw.webapp.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueRentRequestValidator.class})
public @interface UniqueRentRequest {

    String message() default "Cant send two requests for the same article and time period";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String startDate();

    String endDate();

    String articleId();

    String renterId();

}
