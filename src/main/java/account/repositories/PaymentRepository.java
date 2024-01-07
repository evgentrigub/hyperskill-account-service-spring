package account.repositories;

import account.models.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByEmployeeEmailOrderByDateDesc(String email);
    boolean existsByEmployeeEmailAndPeriod(String email, String period);
    Payment findByEmployeeEmailAndPeriod(String email, String period);

}