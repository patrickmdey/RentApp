package ar.edu.itba.paw.webapp.forms.Annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final ZoneId TIME_ZONE = ZoneId.systemDefault();

    @Override
    public void initialize(FutureDate futureDate) {}

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        Date date;

        try {
            date = DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }

        Date today = Date.from(LocalDate.now().atStartOfDay(TIME_ZONE).toInstant());

        return date.equals(today) || date.after(today);
    }
}
