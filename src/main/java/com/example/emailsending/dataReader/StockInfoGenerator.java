package com.example.emailsending.dataReader;

import com.example.emailsending.model.Data;
import com.example.emailsending.model.StockData;
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

@Component
public class StockInfoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(StockInfoGenerator.class);

    @Value("${apiKey}")
    private String apiKey;

    @Value("${stocks}")
    private String[] stocks;

    @Autowired
    private Data data;

    private URLParser urlParser = new URLParser();

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

    public void generateInfo() throws ParseException {

        for (int i = 0; i < stocks.length; i++) {
            String stockName = stocks[i];
            StockData stockData = data.getStocks().get(stockName);
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
            stockData.setClosePrice(closePrice);
            stockData.setOpenPrice(openPrice);
            stockData.setDate(getDate());
            logger.info("successfully set all " + stockName + " data");

        }

    }

}
