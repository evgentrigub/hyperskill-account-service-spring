package account.services;

import account.models.PaymentHistoryResponseDto;
import account.models.entities.Payment;
import account.models.entities.User;
import account.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private final UserService userService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public EmployeeService(UserService userService, PaymentRepository paymentRepository) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
    }

    public ResponseEntity<?> getPayments(UserDetailsImpl userDetails, String period) {
        User employee = this.userService.getUser(userDetails.getUsername());

        if (period == null) {
            if (paymentRepository.findAllByEmployeeEmailOrderByDateDesc(employee.getEmail()).isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<PaymentHistoryResponseDto> payments = new ArrayList<>();

            for (Payment payment : paymentRepository.findAllByEmployeeEmailOrderByDateDesc(employee.getEmail())) {
                payments.add(new PaymentHistoryResponseDto(payment));
            }

            return ResponseEntity.ok(payments);
        }

        if (!paymentRepository.existsByEmployeeEmailAndPeriod(employee.getEmail(), period)) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        Payment payment = paymentRepository.findByEmployeeEmailAndPeriod(employee.getEmail(), period);

        return ResponseEntity.ok(new PaymentHistoryResponseDto(payment));
    }
}