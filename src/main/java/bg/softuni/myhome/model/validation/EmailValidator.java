package bg.softuni.myhome.model.validation;

import bg.softuni.myhome.model.validation.annotations.UniqueEmail;
import bg.softuni.myhome.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    public EmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.findByEmailIfUserExists(value);
    }
}
