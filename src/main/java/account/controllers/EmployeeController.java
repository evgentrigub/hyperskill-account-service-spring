package account.controllers;

import account.security.UserDetailsImpl;
import account.security.validators.validPeriod.ValidPeriodFormat;
import account.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public EmployeeService employeeService;

    @GetMapping("/payment")
    public ResponseEntity<?> getPayments(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @ValidPeriodFormat @RequestParam(required = false) String period) {
        return employeeService.getPayments(userDetails, period);
    }
}
