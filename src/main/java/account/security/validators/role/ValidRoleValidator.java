package account.security.validators.role;

import account.models.role.RoleType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {
    private String errorMessage;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
        try {
            RoleType.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
    }
}