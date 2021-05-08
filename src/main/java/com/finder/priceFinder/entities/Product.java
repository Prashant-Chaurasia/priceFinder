package com.finder.priceFinder.entities;

public class Product {
    private String sku;
    private String title;
    private String description;
    private Float offerPrice;

    public Product(String sku, String title, String description, Float offerPrice) {
        super();
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.offerPrice = offerPrice;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getOfferPrice() {
        return this.offerPrice;
    }

    public void setOfferPrice(Float offerPrice) {
        this.offerPrice = offerPrice;
    }

}
