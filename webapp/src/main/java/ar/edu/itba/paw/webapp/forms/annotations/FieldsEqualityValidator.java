package ar.edu.itba.paw.webapp.forms.annotations;

import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class FieldsEqualityValidator implements ConstraintValidator<FieldsEquality, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldsEquality constraintAnnotation) {
        firstFieldName = constraintAnnotation.firstFieldName();
        secondFieldName = constraintAnnotation.secondFieldName();
        message = constraintAnnotation.message();
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


            if (!Objects.equals(first,second)) {

                ConstraintValidatorContext.ConstraintViolationBuilder cvb = context.buildConstraintViolationWithTemplate(message);
                cvb.addNode(firstFieldName).addConstraintViolation();

                ConstraintValidatorContext.ConstraintViolationBuilder cvb2 = context.buildConstraintViolationWithTemplate(message);
                cvb2.addNode(secondFieldName).addConstraintViolation();

                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}