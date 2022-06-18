package pl.simple.finance.apiserver.repository.saving;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyDataBuffer;

@Repository
public interface CurrencyDataBufferRepository extends CrudRepository<CurrencyDataBuffer, Long> {
}
