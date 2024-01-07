package account.controllers;

import account.models.role.ChangeRoleRequestDto;
import account.models.user.UserDeletedResponse;
import account.models.user.UserDetailsResponse;
import account.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/user/**")
    public ResponseEntity<List<UserDetailsResponse>> getUserDetails() {
        List<UserDetailsResponse> userList = adminService.getUsers();
        return ResponseEntity.ok(userList);
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<UserDeletedResponse> deleteUser(@PathVariable String email) {
        adminService.deleteUser(email);
        return ResponseEntity.ok(new UserDeletedResponse(email));
    }

    @PutMapping("/user/role/**")
    public ResponseEntity<UserDetailsResponse> changeUserRole(@Valid @RequestBody ChangeRoleRequestDto request) {
        UserDetailsResponse response = adminService.changeUserRole(request);
        return ResponseEntity.ok(response);
    }
}