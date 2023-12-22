package account.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    public String id;

    @NotEmpty
    public String name;

    @NotEmpty
    public String lastname;

    @Email(regexp = "^(.+)@acme.com$" )
    @NotEmpty
    public String email;

    @NotEmpty
    public String password;
}
