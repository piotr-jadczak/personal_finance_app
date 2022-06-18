package pl.simple.finance.apiserver.model.expense;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.simple.finance.apiserver.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Expense implements Comparable<Expense> {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @MinDate(date = {2021, 12, 31})
    private LocalDate date;

    @DecimalMax("1000000.00")
    @DecimalMin("0.01")
    private double amount;

    @Size(max = 64)
    @Column(nullable = true)
    private String note;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ExpenseCategory> categories;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Expense(LocalDate date, double amount, String note, Set<ExpenseCategory> categories, User user) {
        this.date = date;
        this.amount = amount;
        this.note = note;
        this.categories = categories;
        this.user = user;
    }

    @Override
    public int compareTo(Expense o) {
        return date.compareTo(o.getDate());
    }
}
