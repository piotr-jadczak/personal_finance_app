package pl.simple.finance.apiserver.model.saving;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.simple.finance.apiserver.model.saving.cash.Cash;
import pl.simple.finance.apiserver.model.saving.currency.Currency;
import pl.simple.finance.apiserver.model.saving.stock.Stock;
import pl.simple.finance.apiserver.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @OneToMany
    @Getter @Setter
    private Set<Currency> currencies;

    @OneToMany
    @Getter @Setter
    private Set<Stock> stocks;

    @OneToOne
    @Getter @Setter
    private Cash cash;

    @OneToOne(mappedBy = "savings")
    @Getter @Setter
    private User user;

    public Savings(Cash cash) {
        this.cash = cash;
    }
}
