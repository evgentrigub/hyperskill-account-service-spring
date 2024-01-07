package account.models.role;

import account.security.validators.changeRole.ValidChangeRoleOperation;
import account.security.validators.role.ValidRole;
import lombok.Data;

@Data
public class ChangeRoleRequestDto {
    private String user;

    @ValidRole
    private String role;

    @ValidChangeRoleOperation
    private String operation;
}