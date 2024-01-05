package account.controllers;

import account.security.UserDetailsImpl;
import account.services.EmployeeService;
import account.services.validators.ValidPeriodFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empl")
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/payment")
    public ResponseEntity<?> getPayments(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @ValidPeriodFormat @RequestParam(required = false) String period) {
        return employeeService.getPayments(userDetails, period);
    }
}
