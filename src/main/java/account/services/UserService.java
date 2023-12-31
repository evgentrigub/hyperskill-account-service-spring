package account.services;

import account.models.User;
import account.models.UserDataResponseDto;
import account.models.UserNewPasswordResponseDto;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    Set<String> breachedPasswords = Set.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDataResponseDto signUp(User userData) {
        boolean existed = userRepository.existsUserByEmailIgnoreCase(userData.getEmail());
        if (existed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        isPasswordValid(userData.getPassword());

        String encryptedPassword = passwordEncoder.encode(userData.getPassword());
        userData.setEmail(userData.getEmail().toLowerCase());
        userData.setPassword(encryptedPassword);
        User savedUser = this.userRepository.save(userData);
        return new UserDataResponseDto(savedUser);
    }

    public Optional<User> getUserData(UserDetails userDetails) {
        return userRepository.findByEmailIgnoreCase(userDetails.getUsername());
    }

    public User getUser(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
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
