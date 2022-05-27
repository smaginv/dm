package ru.smaginv.debtmanager.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CurrencyValidatorConstraint implements ConstraintValidator<CurrencyValidator, String> {

    private List<String> values;

    @Override
    public void initialize(CurrencyValidator constraintAnnotation) {
        values = List.copyOf(Currency.getAvailableCurrencies()).stream()
                .map(Currency::getCurrencyCode)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(values))
            return true;
        if (!values.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be: " + values).addConstraintViolation();
            return false;
        }
        return true;
    }
}
