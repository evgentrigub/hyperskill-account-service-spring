package account.security.validators.changeRole;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidChangeRoleOperationValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidChangeRoleOperation {
    String message() default "No such operation exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
