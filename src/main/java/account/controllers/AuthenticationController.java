package account.controllers;

import account.models.User;
import account.models.UserDataResponseDto;
import account.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UserDataResponseDto> signUp(@Valid @RequestBody User user) {
        var dto = this.userService.signUp(user);
        return ResponseEntity.ok(dto);
    }
}
