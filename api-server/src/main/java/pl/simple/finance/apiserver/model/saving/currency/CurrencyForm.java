package pl.simple.finance.apiserver.model.saving.currency;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@Data
public class CurrencyForm {

    @Getter @Setter
    @Min(1)
    @Max(1000000)
    private long quantity;

    @Getter @Setter
    @DecimalMax("1000000.00")
    @DecimalMin("0.01")
    private double avgBought;

    @Getter @Setter
    @NotNull
    private CurrencyData currencyData;
}
