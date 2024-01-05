package account.services;

import account.models.PaymentRequestDto;
import account.models.PaymentsSavedResponseDto;
import account.models.entities.Payment;
import account.models.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final UserService userService;

    @Autowired
    public AccountService(
            UserRepository userRepository,
            PaymentRepository paymentRepository,
            UserService userService
    ) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.userService = userService;
    }

    @Transactional
    public ResponseEntity<PaymentsSavedResponseDto> registerPayments(PaymentRequestDto[] request) {
        List<String> paymentsProcessed = new ArrayList<>();

        for (PaymentRequestDto paymentRequest : request) {
            if (!userRepository.existsUserByEmailIgnoreCase(paymentRequest.getEmployee())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
            }

            if (paymentRepository.existsByEmployeeEmailAndPeriod(
                    paymentRequest.getEmployee(), paymentRequest.getPeriod())
                    || paymentsProcessed.contains(paymentRequest.getEmployee() + paymentRequest.getPeriod())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User-Period combo must be unique!");
            }

            User employee = this.userService.getUser(paymentRequest.getEmployee());
            Payment payment = new Payment();

            payment.setValues(employee, paymentRequest.getPeriod(), paymentRequest.getSalary());

            paymentRepository.save(payment);
            paymentsProcessed.add(paymentRequest.getEmployee() + paymentRequest.getPeriod());
        }

        return ResponseEntity.ok(new PaymentsSavedResponseDto("Added successfully!"));
    }

    @Transactional
    public ResponseEntity<PaymentsSavedResponseDto> updatePayments(PaymentRequestDto request) {
        if (!userRepository.existsUserByEmailIgnoreCase(request.getEmployee())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }

        if (!paymentRepository.existsByEmployeeEmailAndPeriod(request.getEmployee(), request.getPeriod())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User-Period combo not found!");
        }

        Payment payment = paymentRepository.findByEmployeeEmailAndPeriod(request.getEmployee(), request.getPeriod());

        payment.setSalary(request.getSalary());

        paymentRepository.save(payment);

        return ResponseEntity.ok(new PaymentsSavedResponseDto("Updated successfully!"));
    }
}
