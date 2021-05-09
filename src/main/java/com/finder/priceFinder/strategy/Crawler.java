package com.finder.priceFinder.strategy;

import java.io.IOException;
import java.util.Date;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.ProductHtmlInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class Crawler {
    
    private CrawlerStrategy crawlerStrategy;

    public Crawler(CrawlerStrategy crawlerStrategy) {
        this.crawlerStrategy = crawlerStrategy;
    }

    public ProductHtmlInfo getProductHtmlInfo(String url, String sku) {
        String urlToParse = crawlerStrategy.getUrlToCrawl(url, sku); 
        Document doc = crawlUrl(urlToParse);
        ProductHtmlInfo productHtmlInfo = new ProductHtmlInfo(doc.html(), new Date(), sku, urlToParse);
        return productHtmlInfo;
    }

    private Document crawlUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
            .referrer("http://www.amazon.in")
            .get();
            return doc;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Product getProductDetails(ProductHtmlInfo productHtmlInfo) {
        return crawlerStrategy.getProductDetails(productHtmlInfo);
    }

    public String getSkuFromUrl(String url) {
        return crawlerStrategy.getSkuFromUrl(url);
    }
}
