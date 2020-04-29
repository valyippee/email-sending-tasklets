package com.example.emailsending.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "stock_data")
public class StockData {

    @Id
    @Column(name = "Name")
    private String name;

    @Column(name = "Market_Date")
    private String date;

    @Column(name = "Opening_Price")
    private String openPrice;

    @Column(name = "Closing_Price")
    private String closePrice;
//    private String fiftyTwoWeekHigh;
//    private String fiftyTwoWeekLow;
//    private String boughtAtPrice;
//    private String percentageIncrease; //use closing price

    public StockData() {
    }


    public void setName(String name) {
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

//    public String getFiftyTwoWeekHigh() {
//        return fiftyTwoWeekHigh;
//    }
//
//    public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
//        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
//    }
//
//    public String getFiftyTwoWeekLow() {
//        return fiftyTwoWeekLow;
//    }
//
//    public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
//        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
//    }
//
//    public String getBoughtAtPrice() {
//        return boughtAtPrice;
//    }
//
//    public void setBoughtAtPrice(String boughtAtPrice) {
//        this.boughtAtPrice = boughtAtPrice;
//    }
//
//    public String getPercentageIncrease() {
//        return percentageIncrease;
//    }
//
//    public void setPercentageIncrease(String percentageIncrease) {
//        this.percentageIncrease = percentageIncrease;
//    }

    @Override
    public String toString() {
        return "StockData{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", openPrice='" + openPrice + '\'' +
                ", closePrice='" + closePrice + '\'' +
                '}';
    }
}
