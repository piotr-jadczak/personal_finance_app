package pl.simple.finance.apiserver.model.saving.stock;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Data
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String companyName;

    @NotBlank
    @Column(unique = true)
    private String symbol;

    @DecimalMin("0.0")
    private double currentPrice;

    @NotNull
    private LocalTime fetchTime;

    public StockData(String companyName, String symbol) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.currentPrice = 0.0;
        this.fetchTime = LocalTime.of(12,0);
    }

    public void updateStockData(StockDataBuffer buffer) {
        this.currentPrice = buffer.getCurrentPrice();
        this.fetchTime = buffer.getFetchTime();
    }
}
