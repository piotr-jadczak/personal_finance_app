package pl.simple.finance.apiserver.service.contract;

import pl.simple.finance.apiserver.model.saving.stock.StockData;
import pl.simple.finance.apiserver.model.saving.stock.StockDataBuffer;

import java.awt.event.ActionListener;

public interface StockDataService extends ActionListener {

    void updateStocksBufferPrices();
    Iterable<StockDataBuffer> getAllBufferedStocks();
    Iterable<StockData> getAllDataStocks();
    void copyStockDataFromBuffer();
}
