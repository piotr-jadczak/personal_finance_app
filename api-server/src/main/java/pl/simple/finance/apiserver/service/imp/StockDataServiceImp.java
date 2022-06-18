package pl.simple.finance.apiserver.service.imp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.simple.finance.apiserver.model.saving.MarketDataUpdateStatus;
import pl.simple.finance.apiserver.model.saving.stock.StockData;
import pl.simple.finance.apiserver.model.saving.stock.StockDataBuffer;
import pl.simple.finance.apiserver.repository.saving.StockDataBufferRepository;
import pl.simple.finance.apiserver.repository.saving.StockDataRepository;
import pl.simple.finance.apiserver.service.contract.StockDataService;

import javax.transaction.Transactional;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class StockDataServiceImp implements StockDataService {

    private final StockDataBufferRepository bufferRepository;
    private final StockDataRepository stockRepository;

    private static final String googleSearchURL = "https://www.google.com/search?q=";
    private static final String webClassWithPrice = "IsqQVc NprOob wT3VGc";

    public MarketDataUpdateStatus updateStatus;

    @Autowired
    public StockDataServiceImp(StockDataBufferRepository bufferRepository,
                               StockDataRepository stockRepository) {
        this.bufferRepository = bufferRepository;
        this.stockRepository = stockRepository;
        updateStatus = new MarketDataUpdateStatus();
    }

    @Override
    public void updateStocksBufferPrices() {

        Iterable<StockDataBuffer> bufferStocks = bufferRepository.findAll();
        for(StockDataBuffer stock : bufferStocks) {
            updateStockInfo(stock);
        }
        bufferRepository.saveAll(bufferStocks);
    }

    @Override
    public Iterable<StockDataBuffer> getAllBufferedStocks() {
        return bufferRepository.findAll();
    }

    @Override
    public Iterable<StockData> getAllDataStocks() {

        return stockRepository.findAll();
    }

    @Override
    public void copyStockDataFromBuffer() {
        Iterable<StockDataBuffer> stockBuffer = getAllBufferedStocks();
        Map<String, StockDataBuffer> bufferMap = new HashMap<>();
        for(StockDataBuffer buffer : stockBuffer) {
            bufferMap.put(buffer.getSymbol(), buffer);
        }
        Iterable<StockData> stockData = getAllDataStocks();
        for(StockData stock : stockData) {
            stock.updateStockData(bufferMap.get(stock.getSymbol()));
        }
        stockRepository.saveAll(stockData);
    }

    private void updateStockInfo(StockDataBuffer stock) {

        double currencyPrice = 0;
        try {
            Document doc = Jsoup.connect(googleSearchURL + stock.getSearchQuote()).get();
            Element stockPriceContent = doc.getElementsByClass(webClassWithPrice).get(0);
            currencyPrice = Double.parseDouble(stockPriceContent.text().
                    replace(',','.').replaceAll(" ",""));
        }
        catch (IOException e) {
            System.out.println("Error fetching stock " + stock.getCompanyName());
        }

        int hour = LocalTime.now().getHour();
        int minutes = LocalTime.now().getMinute();
        stock.setCurrentPrice(currencyPrice);
        stock.setFetchTime(LocalTime.of(hour, minutes));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateStocksBufferPrices();
        if(!updateStatus.isUpdated()) {
            updateStatus.startUpdate();
            copyStockDataFromBuffer();
            updateStatus.endUpdate();
        }
    }
}
