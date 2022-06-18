package pl.simple.finance.apiserver.service.imp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.simple.finance.apiserver.model.saving.MarketDataUpdateStatus;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyData;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyDataBuffer;
import pl.simple.finance.apiserver.repository.saving.CurrencyDataBufferRepository;
import pl.simple.finance.apiserver.repository.saving.CurrencyDataRepository;
import pl.simple.finance.apiserver.repository.saving.SavingsRepository;
import pl.simple.finance.apiserver.repository.user.UserRepository;
import pl.simple.finance.apiserver.service.contract.CurrencyDataService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyDataServiceImp implements CurrencyDataService {

    private final CurrencyDataBufferRepository bufferRepository;
    private final CurrencyDataRepository dataRepository;
    private final UserRepository userRepository;
    private final SavingsRepository savingsRepository;

    private static final String googleSearchURL = "https://www.google.com/search?q=";
    private static final String webClassWithPrice = "DFlfde SwHCTb";

    public MarketDataUpdateStatus updateStatus;

    @Autowired
    public CurrencyDataServiceImp(CurrencyDataBufferRepository bufferRepository,
                                  CurrencyDataRepository dataRepository,
                                  UserRepository userRepository,
                                  SavingsRepository savingsRepository) {
        this.bufferRepository = bufferRepository;
        this.dataRepository = dataRepository;
        this.updateStatus = new MarketDataUpdateStatus();
        this.userRepository = userRepository;
        this.savingsRepository = savingsRepository;
    }

    @Override
    public void updateCurrencyDataBufferPrices() {

        Iterable<CurrencyDataBuffer> bufferCurrencies = bufferRepository.findAll();
        for(CurrencyDataBuffer currency : bufferCurrencies) {
            updateCurrencyInfo(currency);
        }
        bufferRepository.saveAll(bufferCurrencies);
    }

    @Override
    public Iterable<CurrencyDataBuffer> getAllBufferedCurrencies() {
        return bufferRepository.findAll();
    }

    @Override
    public Iterable<CurrencyData> getAllDataCurrency() {
        return dataRepository.findAll();
    }

    @Override
    public void copyCurrencyDataFromBuffer() {
        Iterable<CurrencyDataBuffer> currencyBuffer = getAllBufferedCurrencies();
        Map<String, CurrencyDataBuffer> bufferMap = new HashMap<>();
        for(CurrencyDataBuffer buffer : currencyBuffer) {
            bufferMap.put(buffer.getSymbol(), buffer);
        }
        Iterable<CurrencyData> currencyData = getAllDataCurrency();
        for(CurrencyData currency : currencyData) {
            currency.updateCurrencyData(bufferMap.get(currency.getSymbol()));
        }
        dataRepository.saveAll(currencyData);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateCurrencyDataBufferPrices();
        if(!updateStatus.isUpdated()) {
            updateStatus.startUpdate();
            copyCurrencyDataFromBuffer();
            updateStatus.endUpdate();
        }
    }

    private void updateCurrencyInfo(CurrencyDataBuffer currency) {

        double stockPrice = 0;
        try {
            Document doc = Jsoup.connect(googleSearchURL + currency.getSearchQuote()).get();
            Element stockPriceContent;
            stockPriceContent = doc.getElementsByClass(webClassWithPrice).get(0);

            stockPrice = Double.parseDouble(stockPriceContent.text().
                    replace(',','.').replaceAll(" ","")
                    .replaceAll("&nbsp;", ""));
        }
        catch (IOException e) {
            System.out.println("Error fetching currency " + currency.getCurrencyName());
        }

        int hour = LocalTime.now().getHour();
        int minutes = LocalTime.now().getMinute();
        currency.setCurrentPrice(stockPrice);
        currency.setFetchTime(LocalTime.of(hour, minutes));
    }
}
