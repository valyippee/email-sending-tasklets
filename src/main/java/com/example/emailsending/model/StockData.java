package com.example.emailsending.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class StockData {

    private String name;
    private String date;
    private String openPrice;
    private String closePrice;
    private String fiftyTwoWeekHigh;
    private String fiftyTwoWeekLow;
    private String boughtAtPrice;
    private String percentageIncrease; //use closing price

    public StockData() {
    }

    public StockData(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    public String getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public String getBoughtAtPrice() {
        return boughtAtPrice;
    }

    public void setBoughtAtPrice(String boughtAtPrice) {
        this.boughtAtPrice = boughtAtPrice;
    }

    public String getPercentageIncrease() {
        return percentageIncrease;
    }

    public void setPercentageIncrease(String percentageIncrease) {
        this.percentageIncrease = percentageIncrease;
    }
}
