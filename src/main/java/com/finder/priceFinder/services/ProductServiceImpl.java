package com.finder.priceFinder.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.finder.priceFinder.entities.ProductHtmlInfo;

@Service
public class ProductServiceImpl implements ProductService{

    @Override
    public ProductHtmlInfo crawlAndSave(String url, String sku) {
        String urlToParse = url;
        
        if (url != null){
            sku = getSkuFromUrl(url);
        } 

        if (sku != null) {
            urlToParse = "https://www.amazon.in/dp/" + sku;
        }
        
        Document doc = crawlUrl(urlToParse);
        // if (doc == null) {
        //     return "Something went wrong";
        // }

        ProductHtmlInfo productHtmlInfo = new ProductHtmlInfo((long) 1, doc.html(), new Date(), sku, url);
        System.out.println(productHtmlInfo);
        saveProductDetails(doc);
        return productHtmlInfo;
    }

    private String getSkuFromUrl(String url) {
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

    private void saveProductDetails(Document document) {
        Element title = document.select("#productTitle").first();
        Element price = document.select("#priceblock_ourprice").first();
        Elements desc = document.select("#productDescription_feature_div :nth-child(2)");
        int len = desc.size();
        String description = desc.get(len-1).text();
        System.out.println(title.text());
        System.out.println(price.text());
        System.out.println(description);
    }
    
}
