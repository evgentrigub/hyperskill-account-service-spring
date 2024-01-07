package account.services;

import account.exceptions.InvalidDeleteRequestException;
import account.models.entities.Role;
import account.models.entities.User;
import account.models.role.ChangeRoleRequestDto;
import account.models.role.RoleType;
import account.repositories.RoleRepository;
import account.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ChangeRoleService {

    private ChangeRoleRequestDto requestDto;
    private User user;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public User getUserWithChangedRole(ChangeRoleRequestDto requestDto, User user) {
        this.requestDto = requestDto;
        this.user = user;

        if (requestDto.getOperation().equals("GRANT")) {
            if (isInvalidRoleCombo()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
            }

            Role role = new Role();
            role.setRoleType(RoleType.valueOf(requestDto.getRole()));

            roleRepository.save(role);
            user.getRoles().add(role);
        } else {
            if (!userContainsRole()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
            }

            if (isDeletingAdministrator()) {
                throw new InvalidDeleteRequestException();
            }

            if (isOnlyRole()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
            }

            for (Role role : user.getRoles()) {
                if (role.getRoleType().equals(RoleType.valueOf(requestDto.getRole()))) {
                    user.getRoles().remove(role);
                    break;
                }
            }
        }

        userRepository.save(user);
        return user;
    }

    private boolean userContainsRole() {
        List<RoleType> userRoles = user.getRoles().stream().map(Role::getRoleType).toList();
        return userRoles.contains(RoleType.valueOf(requestDto.getRole()));
    }

    private boolean isOnlyRole() {
        return user.getRoles().size() == 1;
    }

    private boolean isDeletingAdministrator() {
        return user.getRoles().stream().map(Role::getRoleType).toList().contains(RoleType.ADMINISTRATOR)
                && RoleType.valueOf(requestDto.getRole()).equals(RoleType.ADMINISTRATOR);
    }

    private boolean isInvalidRoleCombo() {
        List<RoleType> userRoles = user.getRoles().stream().map(Role::getRoleType).toList();
        return RoleType.valueOf(requestDto.getRole()).equals(RoleType.ADMINISTRATOR)
                || userRoles.contains(RoleType.ADMINISTRATOR);
    }
}
