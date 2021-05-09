package com.finder.priceFinder.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_html_info")
public class ProductHtmlInfo {
    
    @Id
    @Column(name = "sku", updatable = false)
    private String sku;

    @Column(name = "html", nullable = false, columnDefinition = "TEXT")
    private String html;
    
    @Column(name = "crawled_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Date crawledAt;
    
    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    private String url;

    public ProductHtmlInfo() {
    }

    public ProductHtmlInfo(String html, Date crawledAt, String sku, String url) {
        super();
        this.html = html;
        this.crawledAt = crawledAt;
        this.sku = sku;
        this.url = url;
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

    @Override
    public String toString() {
        return "{" +
            " sku='" + getSku() + "'" +
            ", crawledAt='" + getCrawledAt() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }

}
