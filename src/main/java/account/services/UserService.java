package account.services;

import account.exceptions.UserExistException;
import account.models.User;
import account.models.UserDataResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDataResponseDto signUp(User userData) {
        boolean existed = userRepository.existsUserByEmailIgnoreCase(userData.getEmail());
        if (existed) {
            throw new UserExistException();
        }

        String encryptedPassword = passwordEncoder.encode(userData.getPassword());
        userData.setPassword(encryptedPassword);
        User savedUser = this.userRepository.save(userData);
        return new UserDataResponseDto(savedUser);
    }

    public Optional<User> getUserData(UserDetails userDetails) {
        return userRepository.findByEmailIgnoreCase(userDetails.getUsername());
    }

}
