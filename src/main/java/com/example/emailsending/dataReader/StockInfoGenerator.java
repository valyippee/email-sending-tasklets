package com.example.emailsending.dataReader;

import com.example.emailsending.model.StockData;
import com.example.emailsending.model.StockDataId;
import com.example.emailsending.repositories.StockDataRepository;
import org.apache.commons.lang.ArrayUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Component
public class StockInfoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(StockInfoGenerator.class);

    @Autowired
    private StockDataRepository stockDataRepository;

    @Value("${api.key}")
    private String apiKey;

    @Value("${stocks}")
    private String[] stocks;

    @Value("${bought.at.prices}")
    private String[] boughtAtPrices;

    @Value("${bought.at.quantities}")
    private String[] boughtAtQuantities;

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
     * @return the previous market opening date in yyyy-MM-dd format
     */
    public static String getPreviousMarketDate() {
        Calendar calendar = Calendar.getInstance();
        byte minusDays = -2;
        // check if it is a weekday/ weekend
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            minusDays = -3;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            minusDays = -4;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            minusDays = -5;
        }
        calendar.add(Calendar.DATE, minusDays);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     *
     * @return an arraylist of String with the names of stocks not in database
     * for the latest market opening date
     */
    private ArrayList<String> checkDatabase() {
        ArrayList<StockDataId> stockDataIds = new ArrayList<StockDataId>();

        for (String stock : stocks) {
            stockDataIds.add(new StockDataId(stock, getDate()));
        }

        ArrayList<String> stocksNotInDatabase = new ArrayList<String>();

        for (StockDataId stockDataId : stockDataIds) {
            if (!stockDataRepository.existsById(stockDataId)) {
                stocksNotInDatabase.add(stockDataId.getName());
            }
        }
        return stocksNotInDatabase;
    }

    /**
     *
     * @param stockName
     * @return JSONObject with the latest market opening date's data
     * @throws ParseException
     */
    private JSONObject requestInfo(String stockName, String date) throws ParseException {
        logger.info("requesting info for " + stockName + ", " + date);
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" +
                stockName +
                "&apikey=" +
                apiKey;
        JSONObject jsonObject = urlParser.extractData(url);
        JSONObject timeSeries = (JSONObject) jsonObject.get("Time Series (Daily)");
        logger.info("time series: " + timeSeries);
        logger.info("obtained time series of " + stockName + " as a json object");
        return timeSeries;
    }

    /**
     *
     * @param stockData
     * @return percentage increase of closing price from previous market date
     * @throws ParseException
     */
    private String calculatePercentageIncrease(StockData stockData, JSONObject timeSeries) {
        String stockName = stockData.getName();
        String previousMarketDate = stockData.getPreviousMarketDate();
        StockDataId previousStockDataId = new StockDataId(stockName, previousMarketDate);
        String currentClosePrice = stockData.getClosePrice();
        String increase = "nil";

        logger.info("calculating percentage increase for " + stockName);

        if (!stockDataRepository.existsById(previousStockDataId)) {
            logger.info("previous day data is not in database");
            JSONObject previousDayData = (JSONObject) timeSeries.get(previousMarketDate);
            String openPrice = (String) previousDayData.get("1. open");
            String closePrice = (String) previousDayData.get("4. close");
            StockData previousStockData = new StockData();
            previousStockData.setOpenPrice(openPrice);
            previousStockData.setClosePrice(closePrice);
            previousStockData.setName(stockName);
            previousStockData.setDate(previousMarketDate);
            stockDataRepository.save(previousStockData);
        }

        String previousClosePrice = stockDataRepository.findById(previousStockDataId).get().getClosePrice();
        logger.info("Previous close price: " + previousClosePrice);

        increase = String.valueOf((double) ((Double.parseDouble(currentClosePrice) - Double.parseDouble(previousClosePrice))
                / Double.parseDouble(previousClosePrice)) * 100);

        logger.info("Percentage increase calculated for " + stockName + ": " + increase);

        return String.format("%.2f", Double.parseDouble(increase));
    }

    /**
     * @param stockData
     * @return profit made from that stock
     */
    public String calculateProfit(StockData stockData) {
        String stockProfit = "nil";
        if (!stockData.getBoughtAtPrice().equals("nil")) {
            stockProfit = String.valueOf(
                    (Double.parseDouble(stockData.getClosePrice()) - Double.parseDouble(stockData.getBoughtAtPrice()))
                    * Double.parseDouble(stockData.getBoughtAtQuantity()));
            return String.format("%.2f", Double.parseDouble(stockProfit));
        }
        return stockProfit;

    }

    /**
     * @return
     * @throws ParseException
     */
    public void settingInfo() throws ParseException {
        ArrayList<String> stocksNotInDatabase = checkDatabase();
        for (int i = 0; i < stocksNotInDatabase.size(); i++) {
            String stockName = stocksNotInDatabase.get(i);
            String marketDate = getDate();
            StockData stockData = new StockData();
            JSONObject timeSeries = requestInfo(stockName, marketDate);
            JSONObject currentData = (JSONObject) timeSeries.get(getDate());
            logger.info("current data: " + currentData);
            String openPrice = (String) currentData.get("1. open");
            String closePrice = (String) currentData.get("4. close");

            stockData.setName(stockName);
            stockData.setClosePrice(closePrice);
            stockData.setOpenPrice(openPrice);
            stockData.setDate(marketDate);
            stockData.setPreviousMarketDate(getPreviousMarketDate());

            // setting boughtAtPrice and quantity
            logger.info("setting bought at price and quantity");
            Integer index = ArrayUtils.indexOf(stocks, stockName);
            stockData.setBoughtAtPrice(boughtAtPrices[index]);
            stockData.setBoughtAtQuantity(boughtAtQuantities[index]);

            // setting profit
            logger.info("setting profit");
            stockData.setProfit(calculateProfit(stockData));

            // setting percentage increase
            logger.info("setting percentage increase");
            stockData.setPercentageIncrease(calculatePercentageIncrease(stockData, timeSeries));

            // save to database
            logger.info("saving data: " + stockName + ", " + getDate());
            stockDataRepository.save(stockData);
        }
        logger.info("successfully extracted all data");
    }


}
