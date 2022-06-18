package pl.simple.finance.apiserver.repository.saving;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.simple.finance.apiserver.model.saving.Savings;
import pl.simple.finance.apiserver.model.saving.currency.Currency;
import pl.simple.finance.apiserver.model.saving.stock.Stock;

import java.util.Set;

@Repository
public interface SavingsRepository extends CrudRepository<Savings, Long> {

    @Query("SELECT s.currencies FROM Savings s WHERE s.id=?1")
    Set<Currency> getAllSavingsCurrencies(long savingsId);

    @Query("SELECT s.stocks FROM Savings s WHERE s.id=?1")
    Set<Stock> getAllSavingsStocks(long savingsId);
}
