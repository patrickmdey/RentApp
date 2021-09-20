package ar.edu.itba.paw.webapp.forms.Annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GreaterDateValidator implements ConstraintValidator<GreaterDate, Object> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
            Date first = getFieldValue(o, baseField);
            Date second = getFieldValue(o, matchField);

            toReturn = first.equals(second) || second.after(first);
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

    private Date getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field dateField = clazz.getDeclaredField(fieldName);
        dateField.setAccessible(true);
        return DATE_FORMAT.parse((String) dateField.get(object));
    }

}
