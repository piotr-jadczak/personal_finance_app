package pl.simple.finance.apiserver.repository.saving;

import org.springframework.data.repository.CrudRepository;
import pl.simple.finance.apiserver.model.saving.stock.StockDataBuffer;

public interface StockDataBufferRepository extends CrudRepository<StockDataBuffer, Long> {
}
