package pl.simple.finance.apiserver.model.saving.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import pl.simple.finance.apiserver.repository.saving.StockDataBufferRepository;
import pl.simple.finance.apiserver.repository.saving.StockDataRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockDataInputReader {

    private static final String delimiter = ",";
    private final StockDataBufferRepository bufferRepository;
    private final StockDataRepository stockRepository;
    private final ResourceLoader resourceLoader;
    private StockDataInput[] dataInput;

    @Autowired
    public StockDataInputReader(StockDataBufferRepository bufferRepository,
                                StockDataRepository stockRepository,
                                ResourceLoader resourceLoader) {
        this.bufferRepository = bufferRepository;
        this.stockRepository = stockRepository;
        this.resourceLoader = resourceLoader;
    }

    public void getData() {

        List<StockDataInput> stockDataList = new ArrayList<>();
        try (InputStream in = resourceLoader.getResource("classpath:stock_input.csv").getInputStream()){
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            String[] rowData;
            while((line = br.readLine()) != null) {
                rowData = line.split(delimiter);
                stockDataList.add(new StockDataInput(rowData[0], rowData[1], rowData[2]));
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
        catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        dataInput = stockDataList.toArray(new StockDataInput[0]);
    }

    public void loadStockBufferData() {

        List<StockDataBuffer> stocksBuffer = new ArrayList<>();
        for(StockDataInput stock : dataInput) {
            stocksBuffer.add(new StockDataBuffer(stock.getCompanyName(),
                    stock.getSymbol(), stock.getSearchQuote()));
        }
        bufferRepository.saveAll(stocksBuffer);
    }

    public void loadStockData() {

        List<StockData> stocksData = new ArrayList<>();
        for(StockDataInput stock : dataInput) {
            stocksData.add(new StockData(stock.getCompanyName(),
                    stock.getSymbol()));
        }
        stockRepository.saveAll(stocksData);
    }

    public void initialSetUp() {
        getData();
        loadStockBufferData();
        loadStockData();
    }

}
