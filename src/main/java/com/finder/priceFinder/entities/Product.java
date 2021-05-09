package com.finder.priceFinder.entities;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "sku")
    private String sku;
    
    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "offer_price", columnDefinition = "NUMERIC")
    private Float offerPrice;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Date createdAt;

    @ElementCollection
    private Map<String, String> ratings;

    public Product() {
    }

    public Product(String id, String sku, String title, String description, Float offerPrice, Date createdAt, Map<String,String> ratings) {
        this.id = id;
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.offerPrice = offerPrice;
        this.createdAt = createdAt;
        this.ratings = ratings;
    }

    public Product(String sku, String title, String description, Float offerPrice, Date createdAt, Map<String,String> ratings) {
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.offerPrice = offerPrice;
        this.createdAt = createdAt;
        this.ratings = ratings;
    }


    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }    

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String,String> getRatings() {
        return this.ratings;
    }

    public void setRatings(Map<String,String> ratings) {
        this.ratings = ratings;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", sku='" + getSku() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", offerPrice='" + getOfferPrice() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", ratings='" + getRatings() + "'" +
            "}";
    }


}
