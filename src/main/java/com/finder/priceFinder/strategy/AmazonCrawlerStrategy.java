package com.finder.priceFinder.strategy;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.ProductHtmlInfo;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component("amazon")
public class AmazonCrawlerStrategy implements CrawlerStrategy {

    final String BASE_URL = "https://www.amazon.in/dp/";

    @Override
    public String getUrlToCrawl(String url, String sku) {
        String urlToParse = url;
        if (url != null){
            sku = getSkuFromUrl(url);
        }

        if (sku != null) {
            urlToParse =  BASE_URL + sku;
        }
        return urlToParse;
    }

    @Override
    public String getSkuFromUrl(String url) {
        String sku = null;
        Pattern pattern = Pattern.compile("dp/\\w+");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()){
            String matchSplit[] = matcher.group().split("/");
            if (matchSplit.length == 2) {
                sku = matchSplit[1];
            }
        }
        return sku;
    }

    private Float getOfferPrice(Document document) {
        // To get the price, return in form of $ 240.0
        String priceData = document.select("#priceblock_ourprice").first().text();
        Float offerPrice = 0F;
        String currencyPrice[] = priceData.split(" ");
        assert currencyPrice.length == 2: "Couldn't parse the price";
        offerPrice = Float.parseFloat(currencyPrice[1]);
        return offerPrice;
    }

    private String getTitle (Document document) {
        // To get the title from the page
        return document.select("#productTitle").first().text();
    }

    private String getDescription(Document document) {
        // To get the description, there are multiple line, last one is the description
        Elements desc = document.select("#productDescription");
        int len = desc.size();
        String description = desc.get(len-1).text();
        return description;
    }

    @Override
    public Product getProductDetails(ProductHtmlInfo productHtmlInfo) {
        Document document = Jsoup.parse(productHtmlInfo.getHtml());

        String title = getTitle(document);
        float offerPrice = getOfferPrice(document);
        String description = getDescription(document); 
        Date createdAt = new Date();
        Product product = new Product(productHtmlInfo.getSku(), title, description, offerPrice, createdAt);
        return product;
    }
    
}
