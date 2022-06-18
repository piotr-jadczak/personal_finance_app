package pl.simple.finance.apiserver.model.saving.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import pl.simple.finance.apiserver.repository.saving.CurrencyDataBufferRepository;
import pl.simple.finance.apiserver.repository.saving.CurrencyDataRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyDataInputReader {

    private static final String delimiter = ",";

    private final CurrencyDataBufferRepository bufferRepository;
    private final CurrencyDataRepository currencyRepository;
    private final ResourceLoader resourceLoader;
    private CurrencyDataInput[] dataInput;

    @Autowired
    public CurrencyDataInputReader(CurrencyDataBufferRepository bufferRepository,
                                   CurrencyDataRepository currencyRepository,
                                   ResourceLoader resourceLoader) {
        this.bufferRepository = bufferRepository;
        this.currencyRepository = currencyRepository;
        this.resourceLoader = resourceLoader;
    }

    public void getData() {

        List<CurrencyDataInput> currencyDataList = new ArrayList<>();
        try (InputStream in = resourceLoader.getResource("classpath:currency_input.csv").getInputStream()){
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            String[] rowData;
            while((line = br.readLine()) != null) {
                rowData = line.split(delimiter);
                currencyDataList.add(new CurrencyDataInput(rowData[0], rowData[1], rowData[2]));
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
        catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        dataInput = currencyDataList.toArray(new CurrencyDataInput[0]);
    }

    public void loadCurrencyBufferData() {

        List<CurrencyDataBuffer> currencyBuffer = new ArrayList<>();
        for(CurrencyDataInput currency : dataInput) {
            currencyBuffer.add(new CurrencyDataBuffer(currency.getCurrencyName(),
                    currency.getSymbol(), currency.getSearchQuote()));
        }
        bufferRepository.saveAll(currencyBuffer);
    }

    public void loadCurrencyData() {

        List<CurrencyData> currencyData= new ArrayList<>();
        for(CurrencyDataInput currency : dataInput) {
            currencyData.add(new CurrencyData(currency.getCurrencyName(),
                    currency.getSymbol()));
        }
        currencyRepository.saveAll(currencyData);
    }

    public void initialSetUp() {
        getData();
        loadCurrencyBufferData();
        loadCurrencyData();
    }
}
