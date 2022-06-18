package pl.simple.finance.apiserver.model.saving.cash;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Entity
@NoArgsConstructor
public class Cash {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @DecimalMin("0.0")
    @DecimalMax("1000000000")
    @Getter @Setter
    private double amount;

    public Cash(double amount) {
        this.amount = amount;
    }
}
