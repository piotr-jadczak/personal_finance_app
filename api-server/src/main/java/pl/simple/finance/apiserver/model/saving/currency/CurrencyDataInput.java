package pl.simple.finance.apiserver.model.saving.currency;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CurrencyDataInput {

    private String currencyName;
    private String symbol;
    private String searchQuote;
}
