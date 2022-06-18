package pl.simple.finance.apiserver.model.saving.currency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Min(1)
    @Max(1000000)
    @Getter @Setter
    private long quantity;

    @DecimalMax("1000000.00")
    @DecimalMin("0.01")
    @Getter @Setter
    private double avgBought;

    @ManyToOne
    @Getter @Setter
    private CurrencyData currencyData;

    public Currency(long quantity, double avgBought, CurrencyData currencyData) {
        this.quantity = quantity;
        this.avgBought = avgBought;
        this.currencyData = currencyData;
    }
}
