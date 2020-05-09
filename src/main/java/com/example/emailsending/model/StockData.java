package com.example.emailsending.model;

import javax.persistence.*;


@Entity
@IdClass(StockDataId.class)
@Table(name = "stock_data")
public class StockData {

    @Id
    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "market_date")
    private String date;

    @Column(name = "opening_price")
    private String openPrice;

    @Column(name = "closing_price")
    private String closePrice;

    @Column(name = "bought_at_price")
    private String boughtAtPrice;

    @Column(name = "bought_at_quantity")
    private String boughtAtQuantity;

    @Column(name = "profit")
    private String profit;

    @Column(name = "percentage_increase")
    private String percentageIncrease; //use closing price

    @Column(name = "previous_market_date")
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
