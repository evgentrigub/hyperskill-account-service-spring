package account.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNewPasswordRequestDto {
    @JsonProperty("new_password")
    @NotEmpty()
    private String newPassword;
}
