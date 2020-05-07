package com.example.emailsending.model;

import javax.persistence.*;


@Entity
@IdClass(StockDataId.class)
@Table(name = "stock_data")
public class StockData {

    @Id
    @Column(name = "Name")
    private String name;

    @Id
    @Column(name = "Market_Date")
    private String date;

    @Column(name = "Opening_Price")
    private String openPrice;

    @Column(name = "Closing_Price")
    private String closePrice;

    @Column(name = "Bought_At_Price")
    private String boughtAtPrice;

    @Column(name = "Bought_At_Quantity")
    private String boughtAtQuantity;

    @Column(name = "Profit")
    private String profit;

    @Column(name = "Percentage_Increase")
    private String percentageIncrease; //use closing price

    @Column(name = "Previous_Market_Date")
    private String previousMarketDate;

    public StockData() {
    }

    public String getPreviousMarketDate() {
        return previousMarketDate;
    }

    public void setPreviousMarketDate(String previousMarketDate) {
        this.previousMarketDate = previousMarketDate;
    }

    public String getPercentageIncrease() {
        return percentageIncrease;
    }

    public void setPercentageIncrease(String percentageIncrease) {
        this.percentageIncrease = percentageIncrease;
    }

    public String getBoughtAtQuantity() {
        return boughtAtQuantity;
    }

    public void setBoughtAtQuantity(String boughtAtQuantity) {
        this.boughtAtQuantity = boughtAtQuantity;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
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

    public String getBoughtAtPrice() {
        return boughtAtPrice;
    }

    public void setBoughtAtPrice(String boughtAtPrice) {
        this.boughtAtPrice = boughtAtPrice;
    }

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
