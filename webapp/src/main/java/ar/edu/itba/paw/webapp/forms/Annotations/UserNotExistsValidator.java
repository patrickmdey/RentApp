package ar.edu.itba.paw.webapp.forms.Annotations;

import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNotExistsValidator implements ConstraintValidator<UserNotExists, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UserNotExists userNotExists) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.findByEmail(email).isPresent();
    }
}
