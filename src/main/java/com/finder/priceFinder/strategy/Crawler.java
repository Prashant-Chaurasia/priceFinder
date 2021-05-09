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
        System.out.println(productHtmlInfo.toString());
        return productHtmlInfo;
    }

    private Document crawlUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
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
