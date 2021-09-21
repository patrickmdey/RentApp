package ar.edu.itba.paw.webapp.forms.Annotations;

import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldsEqualityValidator implements ConstraintValidator<FieldsEquality, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldsEquality constraintAnnotation) {
//        char[] arr = constraintAnnotation.firstFieldName().toCharArray();
//        arr[0] = Character.toUpperCase(arr[0]);
//        firstFieldName = String.copyValueOf(arr);
        firstFieldName = constraintAnnotation.firstFieldName();
//
//        arr = constraintAnnotation.secondFieldName().toCharArray();
//        arr[0] = Character.toUpperCase(arr[0]);
//        secondFieldName = String.copyValueOf(arr);
        secondFieldName = constraintAnnotation.secondFieldName();

        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        try {
            Class<?> clazz = value.getClass();

//            char[] arr = firstFieldName.toCharArray();
//            arr[0] = Character.toUpperCase(arr[0]);
//            String firstMethodName = "get" + String.copyValueOf(arr);

//            Method firstField = ReflectionUtils.findMethod(clazz, firstMethodName);

            Field firstField = ReflectionUtils.findField(clazz, firstFieldName);
            firstField.setAccessible(true);
            Object first = firstField.get(value);

//            arr = secondFieldName.toCharArray();
//            arr[0] = Character.toUpperCase(arr[0]);
//            String secondMethodName = "get" + String.copyValueOf(arr);
//
//            Method secondField = ReflectionUtils.findMethod(clazz, secondMethodName);
//            Object second = secondField.invoke(value);

            Field secondField = ReflectionUtils.findField(clazz, secondFieldName);
            secondField.setAccessible(true);
            Object second = secondField.get(value);

            if (first != null && second != null && !first.equals(second)) {

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