package account.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user", nullable = false)
    public Long id;

    @NotEmpty(message = "name required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "lastname required")
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @NotEmpty
    @Email(regexp = "^(.+)@acme.com$", message = "wrong email")
    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "password required")
    @Column(name = "password", nullable = false)
    private String password;
}
