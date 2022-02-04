package ar.edu.itba.paw.webapp.annotations;

import ar.edu.itba.paw.interfaces.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNotExistsValidator implements ConstraintValidator<UserNotExists, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserNotExists userNotExists) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.findByEmail(email).isPresent();
    }
}
