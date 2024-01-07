package account.security.validators.validPeriod;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPeriodFormatValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriodFormat {
    String message() default "Period is formatted incorrectly";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}