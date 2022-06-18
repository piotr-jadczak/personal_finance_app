package pl.simple.finance.apiserver.repository.saving;

import org.springframework.data.repository.CrudRepository;
import pl.simple.finance.apiserver.model.saving.stock.Stock;

public interface StockRepository extends CrudRepository<Stock, Long> {
}
