package ar.edu.itba.paw.webapp.forms.Annotations;

import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldsEqualityValidator implements ConstraintValidator<FieldsEquality, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldsEquality constraintAnnotation) {
        firstFieldName = constraintAnnotation.firstFieldName();
        secondFieldName = constraintAnnotation.secondFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        try {
            Class<?> clazz = value.getClass();

            Field firstField = ReflectionUtils.findField(clazz, firstFieldName);
            firstField.setAccessible(true);
            Object first = firstField.get(value);

            Field secondField = ReflectionUtils.findField(clazz, secondFieldName);
            secondField.setAccessible(true);
            Object second = secondField.get(value);

            if (first != null && second != null && !first.equals(second)) {
                ConstraintValidatorContext.ConstraintViolationBuilder cvb = context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate());
                cvb.addNode(firstFieldName).addConstraintViolation();
                cvb.addNode(secondFieldName).addConstraintViolation();

                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}