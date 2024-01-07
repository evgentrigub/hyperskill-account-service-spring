package account.models.user;

import lombok.Data;

@Data
public class UserDeletedResponse {
    private String user;
    private String status;

    public UserDeletedResponse(String user) {
        this.user = user;
        this.status = "Deleted successfully!";
    }
}