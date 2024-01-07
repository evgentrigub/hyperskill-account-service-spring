package account.security.validators.changeRole;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidChangeRoleOperationValidator implements ConstraintValidator<ValidChangeRoleOperation, String> {
    private String errorMessage;

    @Override
    public void initialize(ValidChangeRoleOperation constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String operation, ConstraintValidatorContext context) {
        return operation != null && operation.matches("GRANT|REMOVE");
    }
}
