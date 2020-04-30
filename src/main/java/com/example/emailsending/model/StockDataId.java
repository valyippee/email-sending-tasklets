package com.example.emailsending.model;

import java.io.Serializable;

public class StockDataId implements Serializable {

    private String name;
    private String date;

    public StockDataId(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public StockDataId() {
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockDataId)) return false;

        StockDataId that = (StockDataId) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
