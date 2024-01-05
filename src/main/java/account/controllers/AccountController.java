package account.controllers;

import account.models.PaymentRequestDto;
import account.models.PaymentsSavedResponseDto;
import account.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acct")
@Validated
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/payments")
    public ResponseEntity<PaymentsSavedResponseDto> registerPayments(@RequestBody @Valid PaymentRequestDto[] request) {
        return accountService.registerPayments(request);
    }

    @PutMapping("/payments")
    public ResponseEntity<PaymentsSavedResponseDto> updatePayments(@RequestBody @Valid PaymentRequestDto request) {
        return accountService.updatePayments(request);
    }
}