package pl.simple.finance.apiserver.repository.saving;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.simple.finance.apiserver.model.saving.currency.Currency;

@Repository
public interface CurrencyRepository extends PagingAndSortingRepository<Currency, Long> {
}
