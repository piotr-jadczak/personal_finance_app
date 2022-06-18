package pl.simple.finance.apiserver.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.simple.finance.apiserver.model.saving.Savings;
import pl.simple.finance.apiserver.model.saving.cash.Cash;
import pl.simple.finance.apiserver.model.saving.cash.CashForm;
import pl.simple.finance.apiserver.model.saving.currency.Currency;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyForm;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyUpdateForm;
import pl.simple.finance.apiserver.model.saving.stock.Stock;
import pl.simple.finance.apiserver.model.saving.stock.StockForm;
import pl.simple.finance.apiserver.model.saving.stock.StockUpdateForm;
import pl.simple.finance.apiserver.model.user.User;
import pl.simple.finance.apiserver.repository.saving.CashRepository;
import pl.simple.finance.apiserver.repository.saving.CurrencyRepository;
import pl.simple.finance.apiserver.repository.saving.SavingsRepository;
import pl.simple.finance.apiserver.repository.saving.StockRepository;
import pl.simple.finance.apiserver.repository.user.UserRepository;
import pl.simple.finance.apiserver.service.contract.SavingsService;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SavingsServiceImp implements SavingsService {

    private final UserRepository userRepository;
    private final SavingsRepository savingsRepository;
    private final CashRepository cashRepository;
    private final CurrencyRepository currencyRepository;
    private final StockRepository stockRepository;

    @Autowired
    public SavingsServiceImp(UserRepository userRepository,
                             SavingsRepository savingsRepository,
                             CashRepository cashRepository,
                             CurrencyRepository currencyRepository,
                             StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.savingsRepository = savingsRepository;
        this.cashRepository = cashRepository;
        this.currencyRepository = currencyRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Savings createEmptySavings() {

        Cash emptyCash = cashRepository.save(new Cash(0));
        Savings emptySavings = savingsRepository.save(new Savings(emptyCash));
        return emptySavings;
    }

    @Override
    public Cash getUserCash(long userId) {

        return getUserWithId(userId).getSavings().getCash();
    }

    @Override
    public Cash updateUserCash(long userId, CashForm cashForm) {

        Cash cashToUpdate = getUserWithId(userId).getSavings().getCash();
        cashToUpdate.setAmount(cashForm.getAmount());
        return cashToUpdate;
    }

    @Override
    public Currency addCurrency(CurrencyForm currencyForm, long userId) {

        Currency currencyToAdd = new Currency(currencyForm.getQuantity(),
                currencyForm.getAvgBought(), currencyForm.getCurrencyData());
        Currency addedCurrency = currencyRepository.save(currencyToAdd);
        long savingsId = getUserWithId(userId).getSavings().getId();
        Set<Currency> currencySet = savingsRepository.getAllSavingsCurrencies(savingsId);
        currencySet.add(addedCurrency);
        Savings modSavings = savingsRepository.findById(savingsId)
                .orElseThrow(() -> new RuntimeException("Savings " + savingsId + " does not exist"));
        modSavings.setCurrencies(currencySet);

        return addedCurrency;
    }

    @Override
    public List<Currency> getAllUserCurrencies(long userId) {
        long savingsId = getUserWithId(userId).getSavings().getId();
        return savingsRepository.getAllSavingsCurrencies(savingsId).stream()
                .sorted(Comparator.comparingLong(Currency::getId)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCurrency(long currencyId, long userId) {

        if(!currencyRepository.existsById(currencyId)) {
            throw new RuntimeException("No such currency in db");
        }
        long savingsId = getUserWithId(userId).getSavings().getId();
        Set<Currency> currencySet = savingsRepository.getAllSavingsCurrencies(savingsId);
        Savings userSavings = savingsRepository.findById(savingsId)
                .orElseThrow(() -> new RuntimeException("Savings " + savingsId + " does not exist"));
        currencySet.removeIf(c -> c.getId() == currencyId);
        userSavings.setCurrencies(currencySet);
        currencyRepository.deleteById(currencyId);
        return !currencyRepository.existsById(currencyId);
    }

    @Override
    public Currency updateCurrency(CurrencyUpdateForm currencyForm, long currencyId, long userId) {

        long savingsId = getUserWithId(userId).getSavings().getId();
        Set<Currency> currencySet = savingsRepository.getAllSavingsCurrencies(savingsId);
        Currency currencyToUpdate = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new RuntimeException("Currency " + currencyId + " does not exist"));
        if(!currencySet.contains(currencyToUpdate)) {
            throw new RuntimeException("No such currency in user db");
        }
        currencyToUpdate.setAvgBought(currencyForm.getAvgBought());
        currencyToUpdate.setQuantity(currencyForm.getQuantity());
        return currencyRepository.save(currencyToUpdate);
    }

    @Override
    public List<Stock> getAllUserStocks(long userId) {
        long savingsId = getUserWithId(userId).getSavings().getId();
        return savingsRepository.getAllSavingsStocks(savingsId).stream()
                .sorted(Comparator.comparingLong(Stock::getId)).collect(Collectors.toList());
    }

    @Override
    public Stock addStock(StockForm stockForm, long userId) {

        Stock stockToAdd = new Stock(stockForm.getQuantity(),
                stockForm.getAvgBought(), stockForm.getStockData());
        Stock addedStock = stockRepository.save(stockToAdd);
        long savingsId = getUserWithId(userId).getSavings().getId();
        Set<Stock> stocksSet = savingsRepository.getAllSavingsStocks(savingsId);
        stocksSet.add(addedStock);
        Savings modSavings = savingsRepository.findById(savingsId)
                .orElseThrow(() -> new RuntimeException("Savings " + savingsId + " does not exist"));
        modSavings.setStocks(stocksSet);

        return addedStock;
    }

    @Override
    public Stock updateStock(StockUpdateForm stockForm, long stockId, long userId) {

        long savingsId = getUserWithId(userId).getSavings().getId();
        Set<Stock> stockSet = savingsRepository.getAllSavingsStocks(savingsId);
        Stock stockToUpdate = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock " + stockId + " does not exist"));
        if(!stockSet.contains(stockToUpdate)) {
            throw new RuntimeException("No such stock in user db");
        }
        stockToUpdate.setAvgBought(stockForm.getAvgBought());
        stockToUpdate.setQuantity(stockForm.getQuantity());
        return stockRepository.save(stockToUpdate);
    }

    @Override
    public boolean deleteStock(long stockId, long userId) {

        if(!stockRepository.existsById(stockId)) {
            throw new RuntimeException("No such stock in db");
        }
        long savingsId = getUserWithId(userId).getSavings().getId();
        Set<Stock> stockSet = savingsRepository.getAllSavingsStocks(savingsId);
        Savings userSavings = savingsRepository.findById(savingsId)
                .orElseThrow(() -> new RuntimeException("Savings " + savingsId + " does not exist"));
        stockSet.removeIf(c -> c.getId() == stockId);
        userSavings.setStocks(stockSet);
        stockRepository.deleteById(stockId);
        return !stockRepository.existsById(stockId);
    }

    @Override
    public double getUserTotalMoney(long userId) {

        List<Stock> userStocks = getAllUserStocks(userId);
        List<Currency> userCurrencies = getAllUserCurrencies(userId);
        double userCash = getUserCash(userId).getAmount();
        double totalMoney = 0;
        for(Stock stock : userStocks) {
            totalMoney += (stock.getStockData().getCurrentPrice() * stock.getQuantity());
        }
        for(Currency currency : userCurrencies) {
            totalMoney += (currency.getCurrencyData().getCurrentPrice() * currency.getQuantity());
        }
        totalMoney += userCash;
        return totalMoney;
    }

    @Override
    public double getUserBestInvestmentProfit(long userId) {

        List<Stock> userStocks = getAllUserStocks(userId);
        List<Currency> userCurrencies = getAllUserCurrencies(userId);
        double maxProfit = 0;
        for(Stock stock : userStocks) {
            double profit = stock.getQuantity() * (
                    stock.getStockData().getCurrentPrice() - stock.getAvgBought());
            if(profit > maxProfit) {
                maxProfit = profit;
            }
        }
        for(Currency currency : userCurrencies) {
            double profit = currency.getQuantity() * (
                    currency.getCurrencyData().getCurrentPrice() - currency.getAvgBought());
            if(profit > maxProfit) {
                maxProfit = profit;
            }
        }
        return maxProfit;
    }

    @Override
    public double getUserWorstInvestmentLoss(long userId) {

        List<Stock> userStocks = getAllUserStocks(userId);
        List<Currency> userCurrencies = getAllUserCurrencies(userId);
        double maxLoss = 0;
        for(Stock stock : userStocks) {
            double loss = stock.getQuantity() * (
                    stock.getStockData().getCurrentPrice() - stock.getAvgBought());
            if(loss < maxLoss) {
                maxLoss = loss;
            }
        }
        for(Currency currency : userCurrencies) {
            double loss = currency.getQuantity() * (
                    currency.getCurrencyData().getCurrentPrice() - currency.getAvgBought());
            if(loss < maxLoss) {
                maxLoss = loss;
            }
        }
        return maxLoss;
    }

    private User getUserWithId(long userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("user with id " + userId + " does not exist."));
    }

}
