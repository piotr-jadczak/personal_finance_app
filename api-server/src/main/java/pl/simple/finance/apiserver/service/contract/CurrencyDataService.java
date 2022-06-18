package pl.simple.finance.apiserver.service.contract;

import pl.simple.finance.apiserver.model.saving.currency.CurrencyData;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyDataBuffer;

import java.awt.event.ActionListener;

public interface CurrencyDataService extends ActionListener {

    void updateCurrencyDataBufferPrices();
    Iterable<CurrencyDataBuffer> getAllBufferedCurrencies();
    Iterable<CurrencyData> getAllDataCurrency();
    void copyCurrencyDataFromBuffer();
}
