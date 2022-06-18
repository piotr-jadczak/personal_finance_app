package pl.simple.finance.apiserver.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.simple.finance.apiserver.controller.helper.SecurityContextHandler;
import pl.simple.finance.apiserver.model.saving.cash.Cash;
import pl.simple.finance.apiserver.model.saving.cash.CashForm;
import pl.simple.finance.apiserver.model.saving.currency.Currency;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyData;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyForm;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyUpdateForm;
import pl.simple.finance.apiserver.model.saving.stock.Stock;
import pl.simple.finance.apiserver.model.saving.stock.StockData;
import pl.simple.finance.apiserver.model.saving.stock.StockForm;
import pl.simple.finance.apiserver.model.saving.stock.StockUpdateForm;
import pl.simple.finance.apiserver.service.contract.CurrencyDataService;
import pl.simple.finance.apiserver.service.contract.SavingsService;
import pl.simple.finance.apiserver.service.contract.StockDataService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/savings")
public class SavingsController {

    private final SavingsService savingsService;
    private final CurrencyDataService currencyDataService;
    private final StockDataService stockDataService;

    @Autowired
    public SavingsController(SavingsService savingsService,
                             CurrencyDataService currencyDataService,
                             StockDataService stockDataService) {
        this.savingsService = savingsService;
        this.currencyDataService = currencyDataService;
        this.stockDataService = stockDataService;
    }

    @GetMapping("/cash")
    public Cash getUserCash() {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.getUserCash(userId);
    }

    @PutMapping("/cash")
    public Cash updateUserCash(@RequestBody @Valid CashForm cashForm) {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.updateUserCash(userId, cashForm);
    }

    @GetMapping("/all-currency-data")
    public Iterable<CurrencyData> getAllCurrenciesData() {

        return currencyDataService.getAllDataCurrency();
    }

    @PostMapping("/currencies")
    public Currency addCurrency(@RequestBody @Valid CurrencyForm currencyForm) {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.addCurrency(currencyForm, userId);
    }

    @GetMapping("/currencies")
    public List<Currency> getAllUserCurrencies() {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.getAllUserCurrencies(userId);
    }

    @GetMapping("/available-currency-data")
    public List<CurrencyData> getAllUserAvailableCurrencyData() {

        long userId = SecurityContextHandler.getUserId();
        List<CurrencyData> allData = StreamSupport
                .stream(currencyDataService.getAllDataCurrency().spliterator(), false)
                .collect(Collectors.toList());
        Iterable<Currency> userCurrencies = savingsService.getAllUserCurrencies(userId);
        List<CurrencyData> usedByUserData = StreamSupport
                .stream(userCurrencies.spliterator(), false)
                .map(Currency::getCurrencyData).collect(Collectors.toList());
        allData.removeAll(usedByUserData);
        return allData.stream()
                .sorted(Comparator.comparingLong(CurrencyData::getId))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/currencies/{id}")
    public boolean deleteCurrency(@PathVariable("id") Long id) {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.deleteCurrency(id, userId);
    }

    @PutMapping("/currencies/{id}")
    public Currency updateCurrency(@RequestBody @Valid CurrencyUpdateForm currencyForm,
                                   @PathVariable("id") Long id) {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.updateCurrency(currencyForm, id, userId);
    }

    @GetMapping("/all-stock-data")
    public Iterable<StockData> getAllStockData() {

        return stockDataService.getAllDataStocks();
    }

    @GetMapping("/stocks")
    public List<Stock> getAllUserStocks() {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.getAllUserStocks(userId);
    }

    @PostMapping("/stocks")
    public Stock addStock(@RequestBody @Valid StockForm stockForm) {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.addStock(stockForm, userId);
    }

    @PutMapping("/stocks/{id}")
    public Stock updateStock(@RequestBody @Valid StockUpdateForm stockForm,
                             @PathVariable("id") Long id) {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.updateStock(stockForm, id, userId);
    }

    @DeleteMapping("/stocks/{id}")
    public boolean deleteStock(@PathVariable("id") Long id) {

        long userId = SecurityContextHandler.getUserId();
        return savingsService.deleteStock(id, userId);
    }

    @GetMapping("/available-stock-data")
    public List<StockData> getAllUserAvailableStockData() {

        long userId = SecurityContextHandler.getUserId();
        List<StockData> allData = StreamSupport
                .stream(stockDataService.getAllDataStocks().spliterator(), false)
                .collect(Collectors.toList());
        Iterable<Stock> userStocks = savingsService.getAllUserStocks(userId);
        List<StockData> usedByUserData = StreamSupport
                .stream(userStocks.spliterator(), false)
                .map(Stock::getStockData).collect(Collectors.toList());
        allData.removeAll(usedByUserData);
        return allData.stream()
                .sorted(Comparator.comparingLong(StockData::getId))
                .collect(Collectors.toList());
    }

}
