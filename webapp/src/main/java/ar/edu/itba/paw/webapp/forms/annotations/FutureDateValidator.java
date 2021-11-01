package ar.edu.itba.paw.webapp.forms.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final ZoneId TIME_ZONE = ZoneId.systemDefault();

    @Override
    public void initialize(FutureDate futureDate) {}

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate date;

        try {
            date = LocalDate.parse(dateStr, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }

        LocalDate today = LocalDate.from(LocalDate.now().atStartOfDay(TIME_ZONE).toInstant());

        return date.equals(today) || date.isAfter(today);
    }
}
