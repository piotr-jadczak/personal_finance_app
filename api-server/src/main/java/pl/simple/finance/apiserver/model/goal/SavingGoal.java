package pl.simple.finance.apiserver.model.goal;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.simple.finance.apiserver.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
public class SavingGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin("0.01")
    @DecimalMax("100000000")
    private double amountToSave;

    @Size(max=64)
    @NotEmpty
    private String description;

    @ManyToOne
    User user;

    public SavingGoal(double amountToSave, String description, User user) {
        this.amountToSave = amountToSave;
        this.description = description;
        this.user = user;
    }

}
