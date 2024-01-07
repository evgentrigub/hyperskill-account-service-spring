package account.models.user;

import lombok.Data;

@Data
public class UserNewPasswordResponseDto {
    private String email;
    private String status;

    public UserNewPasswordResponseDto(String email) {
        this.email = email;
        this.status = "The password has been updated successfully";
    }
}
