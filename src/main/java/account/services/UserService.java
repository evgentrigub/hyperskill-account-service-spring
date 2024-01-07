package account.services;

import account.exceptions.UserNotFoundException;
import account.models.entities.Role;
import account.models.entities.User;
import account.models.role.RoleType;
import account.models.user.UserDataResponseDto;
import account.models.user.UserNewPasswordResponseDto;
import account.repositories.RoleRepository;
import account.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    Set<String> breachedPasswords = Set.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    public UserDataResponseDto signUp(User userData) {
        boolean existed = userRepository.existsUserByEmailIgnoreCase(userData.getEmail());
        if (existed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        isPasswordValid(userData.getPassword());
        
        userData.setEmail(userData.getEmail().toLowerCase());
        String encryptedPassword = passwordEncoder.encode(userData.getPassword());
        userData.setPassword(encryptedPassword);

        Role role = new Role();
        RoleType roleType = userRepository.count() == 0 ? RoleType.ADMINISTRATOR : RoleType.USER;
        role.setRoleType(roleType);
        userData.getRoles().add(role);

        roleRepository.save(role);
        userRepository.save(userData);
        return new UserDataResponseDto(userData);
    }

    public Optional<User> getUserData(UserDetails userDetails) {
        return userRepository.findByEmailIgnoreCase(userDetails.getUsername());
    }

    public User getUser(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserNewPasswordResponseDto getUserNewPassword(String userEmail, String newPass) {
        User user = this.getUser(userEmail);
        isPasswordMatches(newPass, user.getPassword());
        isPasswordValid(newPass);
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);

        return new UserNewPasswordResponseDto(userEmail);
    }

    private void isPasswordMatches(String newPassword, String currentPassword) {
        boolean matches = passwordEncoder.matches(newPassword, currentPassword);
        if (matches) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
    }

    private void isPasswordValid(String password) {
        if (password.length() < 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password length must be 12 chars minimum!");
        }
        if (breachedPasswords.contains(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
    }

}
