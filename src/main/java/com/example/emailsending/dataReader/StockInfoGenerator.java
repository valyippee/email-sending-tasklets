package com.example.emailsending.dataReader;

import com.example.emailsending.model.StockData;
import com.example.emailsending.repositories.StockDataRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class StockInfoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(StockInfoGenerator.class);

    @Autowired
    StockDataRepository stockDataRepository;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${stocks}")
    private String[] stocks;

    private URLParser urlParser = new URLParser();

    /**
     *
     * @return the latest market opening date in yyyy-MM-dd format
     */
    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        byte minusDays = -1;
        // check if it is a weekday/ weekend
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            minusDays = -2;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            minusDays = -3;
        }
        calendar.add(Calendar.DATE, minusDays);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     *
     * @return Hashmap<String, StockData>
     * @throws ParseException
     */
    public HashMap<String, StockData> generateInfo() throws ParseException {
        HashMap<String, StockData> stocksCollection = new HashMap<String, StockData>();

        for (int i = 0; i < stocks.length; i++) {
            String stockName = stocks[i];
            StockData stockData = new StockData();
            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" +
                    stockName +
                    "&apikey=" +
                    apiKey;
            JSONObject jsonObject = urlParser.extractData(url);
            JSONObject timeSeries = (JSONObject) jsonObject.get("Time Series (Daily)");
            logger.info("obtained time series of " + stocks[i] + " as a json object");
            JSONObject currentData = (JSONObject) timeSeries.get(getDate());
            logger.info("obtained yesterday's " + stocks[i] + " stock price info as a json object");
            String openPrice = (String) currentData.get("1. open");
            String closePrice = (String) currentData.get("4. close");
            stockData.setName(stockName);
            stockData.setClosePrice(closePrice);
            stockData.setOpenPrice(openPrice);
            stockData.setDate(getDate());
            stocksCollection.put(stockName, stockData);
        }
        logger.info("successfully extracted all data");
        return stocksCollection;
    }

    /**
     * @param stockDataRepository, Hashmap<String, StockData>
     */
    public void saveInfo(StockDataRepository stockDataRepository, HashMap<String, StockData> stocksCollection) {
        for (Map.Entry<String, StockData> entry : stocksCollection.entrySet()) {
            StockData stockData = entry.getValue();
            stockDataRepository.save(stockData);
        }
    }

}
