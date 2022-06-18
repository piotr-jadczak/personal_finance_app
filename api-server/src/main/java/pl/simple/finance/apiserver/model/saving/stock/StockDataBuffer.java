package pl.simple.finance.apiserver.model.saving.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@NoArgsConstructor
@Entity
public class StockDataBuffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @NotBlank
    @Getter @Setter
    private String companyName;

    @NotBlank
    @Getter @Setter
    private String symbol;

    @DecimalMin("0.0")
    @Getter @Setter
    private double currentPrice;

    @NotNull
    @Getter @Setter
    private LocalTime fetchTime;

    @JsonIgnore
    @NotBlank
    @Getter @Setter
    private String searchQuote;

    public StockDataBuffer(String companyName, String symbol, String searchQuote) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.searchQuote = searchQuote;
        fetchTime = LocalTime.of(12, 0);
        currentPrice = 0.0;
    }
}
