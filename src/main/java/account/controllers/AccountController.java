package account.controllers;

import account.models.User;
import account.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empl")
public class AccountController {

    @Autowired
    UserService userService;

    @GetMapping("/payment")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        var user = this.userService.getUserData(userDetails);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not found by email");
        }
        return ResponseEntity.ok(user.get());
    }
}
