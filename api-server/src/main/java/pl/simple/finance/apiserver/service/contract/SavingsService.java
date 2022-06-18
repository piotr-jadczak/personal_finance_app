package pl.simple.finance.apiserver.service.contract;

import pl.simple.finance.apiserver.model.saving.Savings;
import pl.simple.finance.apiserver.model.saving.cash.Cash;
import pl.simple.finance.apiserver.model.saving.cash.CashForm;
import pl.simple.finance.apiserver.model.saving.currency.Currency;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyForm;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyUpdateForm;
import pl.simple.finance.apiserver.model.saving.stock.Stock;
import pl.simple.finance.apiserver.model.saving.stock.StockForm;
import pl.simple.finance.apiserver.model.saving.stock.StockUpdateForm;

import java.util.List;

public interface SavingsService {

    Savings createEmptySavings();
    Cash getUserCash(long userId);
    Cash updateUserCash(long userId, CashForm cashForm);
    Currency addCurrency(CurrencyForm currencyForm, long userId);
    List<Currency> getAllUserCurrencies(long userId);
    boolean deleteCurrency(long currencyId, long userId);
    Currency updateCurrency(CurrencyUpdateForm currencyForm, long currencyId, long userId);
    List<Stock> getAllUserStocks(long userId);
    Stock addStock(StockForm stockForm, long userId);
    Stock updateStock(StockUpdateForm stockForm, long stockId, long userId);
    boolean deleteStock(long stockId, long userId);
    double getUserTotalMoney(long userId);
    double getUserBestInvestmentProfit(long userId);
    double getUserWorstInvestmentLoss(long userId);
}
