package account.services.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPeriodFormatValidator implements ConstraintValidator<ValidPeriodFormat, String> {

    @Override
    public void initialize(ValidPeriodFormat constraintAnnotation) {
        String errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String period, ConstraintValidatorContext context) {
        return period == null || FormattingConstraints.isValidPeriod(period);
    }
}