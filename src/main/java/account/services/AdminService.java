package account.services;

import account.exceptions.InvalidDeleteRequestException;
import account.exceptions.UserNotFoundException;
import account.models.entities.Role;
import account.models.entities.User;
import account.models.role.ChangeRoleRequestDto;
import account.models.role.RoleType;
import account.models.user.UserDetailsResponse;
import account.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChangeRoleService changeRoleService;

    @Autowired
    UserService userService;

    public List<UserDetailsResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDetailsResponse::new).toList();
    }

    @Transactional
    public void deleteUser(String email) {
        if (!userRepository.existsUserByEmailIgnoreCase(email)) {
            throw new UserNotFoundException();
        }

        User user = userService.getUser(email);
        boolean hasRoleAdmin = user.getRoles().stream()
                .map(Role::getRoleType).toList()
                .contains(RoleType.ADMINISTRATOR);

        if (hasRoleAdmin) {
            throw new InvalidDeleteRequestException();
        }

        userRepository.delete(user);
    }

    @Transactional
    public UserDetailsResponse changeUserRole(ChangeRoleRequestDto requestDto) {
        User user = userService.getUser(requestDto.getUser());
        User savedUser = changeRoleService.getUserWithChangedRole(requestDto, user);
        return new UserDetailsResponse(savedUser);
    }
}