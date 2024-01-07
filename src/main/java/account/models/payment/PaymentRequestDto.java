package account.models.payment;

import account.security.validators.validPeriod.ValidPeriodFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PaymentRequestDto {
    @NotEmpty
    private String employee;

    @ValidPeriodFormat
    private String period;

    @Min(value = 0, message = "Salary must be non-negative")
    private long salary;
}