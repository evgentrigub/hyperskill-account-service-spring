package account.controllers;

import account.models.UserData;
import account.models.UserDataResponseDto;
import account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController()
@RequestMapping("/api/auth")
public class AuthenticationController {
    // todo 2 methods

    @Autowired
    AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<UserDataResponseDto> signUpUser(@Valid @RequestBody UserData data) {
        var dto = this.accountService.getUserSignUp(data);
        return ResponseEntity.ok(dto);
    }
}
