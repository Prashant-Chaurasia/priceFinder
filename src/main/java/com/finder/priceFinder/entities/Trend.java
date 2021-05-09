package com.finder.priceFinder.entities;

import java.util.List;

public class Trend {
    
    private String sku;
    
    private List<TimestampPrice> timestampPrices;

    public Trend() {
    }

    public Trend(String sku, List<TimestampPrice> timestampPrices) {
        this.sku = sku;
        this.timestampPrices = timestampPrices;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<TimestampPrice> getTimestampPrices() {
        return this.timestampPrices;
    }

    public void setTimestampPrice(List<TimestampPrice> timestampPrices) {
        this.timestampPrices = timestampPrices;
    }

    @Override
    public String toString() {
        return "{" +
            " sku='" + getSku() + "'" +
            ", timestampPrices='" + getTimestampPrices() + "'" +
            "}";
    }
    
}
