package pl.simple.finance.apiserver.model.saving.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class StockDataInput {

    private String companyName;
    private String symbol;
    private String searchQuote;
}
