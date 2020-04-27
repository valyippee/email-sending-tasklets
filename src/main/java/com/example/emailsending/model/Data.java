package com.example.emailsending.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class Data {

    private static HashMap<String, StockData> stocks = new HashMap<String, StockData>();

    @Value("${stocks}")
    private String[] stocksAnalysed;

    @Value("${boughtAtPrices}")
    private String[] boughtAtPrices;

    public String[] getStocksAnalysed() {
        return stocksAnalysed;
    }

    @PostConstruct
    public void inputStocksData() {
        for (int i = 0; i < stocksAnalysed.length; i++) {
            StockData stockData = new StockData(stocksAnalysed[i]);
            stocks.put(stocksAnalysed[i], stockData);
            stockData.setBoughtAtPrice(boughtAtPrices[i]);
        }
    }

    public HashMap<String, StockData> getStocks() {
        return stocks;
    }




}
