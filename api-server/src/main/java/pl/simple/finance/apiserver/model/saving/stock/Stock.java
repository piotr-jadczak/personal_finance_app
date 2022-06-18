package pl.simple.finance.apiserver.model.saving.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Min(1)
    @Max(1000000)
    @Getter @Setter
    private long quantity;

    @DecimalMin("0.01")
    @Getter @Setter
    private double avgBought;

    @ManyToOne
    @Getter @Setter
    private StockData stockData;

    public Stock(long quantity, double avgBought, StockData stockData) {
        this.quantity = quantity;
        this.avgBought = avgBought;
        this.stockData = stockData;
    }
}