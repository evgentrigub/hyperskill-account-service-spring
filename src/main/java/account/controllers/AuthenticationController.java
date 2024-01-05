package account.controllers;

import account.models.entities.User;
import account.models.UserDataResponseDto;
import account.models.UserNewPasswordRequestDto;
import account.models.UserNewPasswordResponseDto;
import account.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UserDataResponseDto> signUp(@Valid @RequestBody User user) {
        UserDataResponseDto response = this.userService.signUp(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changepass")
    public ResponseEntity<UserNewPasswordResponseDto> setPassword(@RequestBody UserNewPasswordRequestDto dto,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        UserNewPasswordResponseDto response = this.userService.getUserNewPassword(userDetails.getUsername(), dto.getNewPassword());
        return ResponseEntity.ok(response);
    }
}