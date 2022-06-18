package pl.simple.finance.apiserver.model.saving.cash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashForm {

    @DecimalMin("0.0")
    @DecimalMax("1000000000")
    private double amount;
}
