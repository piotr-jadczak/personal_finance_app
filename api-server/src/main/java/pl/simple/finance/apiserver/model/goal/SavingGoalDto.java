package pl.simple.finance.apiserver.model.goal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoalDto {

    @Min(1)
    long id;

    @DecimalMin("0.01")
    @DecimalMax("100000000")
    private double amountToSave;

    @DecimalMin("0")
    @DecimalMax("100000000")
    private double currentlySaved;

    @DecimalMin("0")
    @DecimalMax("100")
    private double percentCompleted;

    @Size(max=64)
    @NotEmpty
    private String description;
}
