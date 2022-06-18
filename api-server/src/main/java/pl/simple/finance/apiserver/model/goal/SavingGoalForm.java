package pl.simple.finance.apiserver.model.goal;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SavingGoalForm {

    @DecimalMin("0.01")
    @DecimalMax("100000000")
    private double amountToSave;

    @Size(max=64)
    @NotEmpty
    private String description;
}
