package ar.edu.itba.paw.webapp.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GreaterDateValidator implements ConstraintValidator<GreaterDate, Object> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String baseField;
    private String matchField;

    @Override
    public void initialize(GreaterDate greaterDate) {
        baseField = greaterDate.baseField();
        matchField = greaterDate.matchField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean toReturn = false;
        try {
            LocalDate first = getFieldValue(o, baseField);
            LocalDate second = getFieldValue(o, matchField);

            toReturn = first.equals(second) || second.isAfter(first);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!toReturn) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Second date should be greater than first")
                    .addNode(baseField).addConstraintViolation();
        }
        return toReturn;
    }

    private LocalDate getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field dateField = clazz.getDeclaredField(fieldName);
        dateField.setAccessible(true);
        return (LocalDate) dateField.get(object);
    }

}
