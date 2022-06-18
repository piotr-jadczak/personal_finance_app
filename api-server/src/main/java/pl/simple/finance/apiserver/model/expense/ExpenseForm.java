package pl.simple.finance.apiserver.model.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ExpenseForm {

    @NotNull
    @MinDate(date = {2021, 12, 31})
    private LocalDate date;

    @DecimalMax("1000000.00")
    @DecimalMin("0.01")
    private double amount;

    @Size(max = 64)
    private String note;

    @NotEmpty
    private List<ExpenseCategory> categories;
}
