package account.models.user;

import account.models.entities.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailsResponse {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private List<String> roles;

    public UserDetailsResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream()
                .map(s -> "ROLE_" + s.getRoleType())
                .sorted()
                .collect(Collectors.toList());
    }
}