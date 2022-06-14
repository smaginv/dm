package ru.smaginv.debtmanager.dm.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooleanValidatorConstraint implements ConstraintValidator<BooleanValidator, String> {

    private List<String> values;

    @Override
    public void initialize(BooleanValidator constraintAnnotation) {
        values = Stream.of(Boolean.TRUE, Boolean.FALSE)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(values))
            return true;
        if (!values.contains(value.toLowerCase())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be: " + values).addConstraintViolation();
            return false;
        }
        return true;
    }
}
