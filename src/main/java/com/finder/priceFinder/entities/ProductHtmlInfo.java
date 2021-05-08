package com.finder.priceFinder.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ProductHtmlInfo {
    @Id
    private Long id;
    
    @Lob
    private String html;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date crawledAt;
    private String sku;
    private String url;

    public ProductHtmlInfo() {
    }

    public ProductHtmlInfo(Long id, String html, Date crawledAt, String sku, String url) {
        super();
        this.id = id;
        this.html = html;
        this.crawledAt = crawledAt;
        this.sku = sku;
        this.url = url;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Date getCrawledAt() {
        return this.crawledAt;
    }

    public void setCrawledAt(Date crawledAt) {
        this.crawledAt = crawledAt;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
