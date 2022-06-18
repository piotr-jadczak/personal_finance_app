package pl.simple.finance.apiserver.repository.saving;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyData;

@Repository
public interface CurrencyDataRepository extends CrudRepository<CurrencyData, Long> {

}
