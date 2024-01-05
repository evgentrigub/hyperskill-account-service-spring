package account.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee", referencedColumnName = "email")
    private User employee;

    @Column(name = "period")
    private String period;

    @Column(name = "salary", nullable = false)
    private long salary;

    @Column(name = "date")
    private LocalDate date;

    public void setValues(User employee, String period, long salary) {
        this.employee = employee;
        this.period = period;
        this.salary = salary;
        this.date = YearMonth.parse(period, DateTimeFormatter.ofPattern("MM-yyyy")).atDay(1);
    }
}