package ru.smaginv.debtmanager.lm.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.smaginv.debtmanager.lm.util.ValidationUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EmailValidatorConstraint implements ConstraintValidator<EmailValidator, String> {

    @Autowired
    private ValidationUtil validationUtil;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (Objects.isNull(validationUtil))
            return true;
        if (!validationUtil.validateEmail(email)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("not valid value: " + email).addConstraintViolation();
            return false;
        }
        return true;
    }
}
