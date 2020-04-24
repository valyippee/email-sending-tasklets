package com.example.emailsending.dataReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GenerateStockPriceInfo {

    private static final Logger logger = LoggerFactory.getLogger(GenerateStockPriceInfo.class);

    @Value("${apiKey}")
    private String apiKey;

    private URLParser urlParser = new URLParser();

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public String generateInfo(String stock) throws ParseException {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" +
                stock +
                "&apikey=" +
                apiKey;
        JSONObject jsonObject = urlParser.extractData(url);
        JSONObject timeSeries = (JSONObject) jsonObject.get("Time Series (Daily)");
        logger.info("obtained time series as a json object");
        JSONObject currentData = (JSONObject) timeSeries.get(getDate());
        logger.info("obtained yesterday's stock price info as a json object");
        String openPrice = (String) currentData.get("1. open");
        String closePrice = (String) currentData.get("4. close");
        return "Stock: VOO" + "\n" +
                "Date: " + getDate() + "\n" +
                "Opening price: " + openPrice + "\n" +
                "Closing price: " + closePrice + "\n";
    }

}
