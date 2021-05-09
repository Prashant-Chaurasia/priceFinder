package com.finder.priceFinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.ProductHtmlInfo;
import com.finder.priceFinder.entities.TimestampPrice;
import com.finder.priceFinder.entities.Trend;
import com.finder.priceFinder.repositories.ProductHtmlInfoRepository;
import com.finder.priceFinder.repositories.ProductRepository;
import com.finder.priceFinder.strategy.AmazonCrawlerStrategy;
import com.finder.priceFinder.strategy.Crawler;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductHtmlInfoRepository productHtmlInfoRepository;

    @Autowired
    private ProductRepository productRepository;

    private Crawler crawler;

    // This is temporary, based on the client we can decide the crawling strategy
    public ProductServiceImpl() {
        this.crawler = new Crawler(new AmazonCrawlerStrategy());
    }

    private Boolean isCrawledInTime(ProductHtmlInfo productHtmlInfo) {
        boolean isCrawled = false;
        if (productHtmlInfo != null) {
            Date now = new Date();
            Date crawledAt = productHtmlInfo.getCrawledAt();

            if (crawledAt != null) {
                long timeDiffInMillis = Math.abs(now.getTime() - productHtmlInfo.getCrawledAt().getTime());
                long diff = TimeUnit.MINUTES.convert(timeDiffInMillis, TimeUnit.MILLISECONDS);
                if (diff < 60) 
                    isCrawled = true;
            }

        }
        return isCrawled;
    } 

    @Override
    public ProductHtmlInfo crawlAndSave(String url, String sku) {
        if (sku == null) {
            sku = crawler.getSkuFromUrl(url);
        }
        ProductHtmlInfo productHtmlInfo = productHtmlInfoRepository.findBySku(sku);
        if (!isCrawledInTime(productHtmlInfo)) {
            productHtmlInfo = crawler.getProductHtmlInfo(url, sku);
            productHtmlInfo = saveProductHtmlInfo(productHtmlInfo);
            extractAndSaveProductDetails(productHtmlInfo);
        }
        return productHtmlInfo;
    }

    private ProductHtmlInfo saveProductHtmlInfo(ProductHtmlInfo productHtmlInfo) {
        productHtmlInfoRepository.save(productHtmlInfo);
        productHtmlInfo = productHtmlInfoRepository.findBySku(productHtmlInfo.getSku());
        return productHtmlInfo;
    }

    private Product extractAndSaveProductDetails(ProductHtmlInfo productHtmlInfo) {
        Product product = crawler.getProductDetails(productHtmlInfo);
        productRepository.save(product);
        return productRepository.findById(product.getId()).get();
    }

    @Override
    public Product getProductDetails(String url, String sku) {
        if (sku == null) {
            sku = crawler.getSkuFromUrl(url);
        }
        ProductHtmlInfo productHtmlInfo = productHtmlInfoRepository.findBySku(sku);
        Product product = productRepository.findBySkuOrderByCreatedAt(sku);
        if (!isCrawledInTime(productHtmlInfo)) {
            productHtmlInfo = crawler.getProductHtmlInfo(url, sku);
            productHtmlInfo = saveProductHtmlInfo(productHtmlInfo);
            product = extractAndSaveProductDetails(productHtmlInfo);
        }
        return product;
    }

    @Override
    public Product getLastUpdatedProductDetails(String url, String sku, String date) {
        if (sku == null) {
            sku = crawler.getSkuFromUrl(url);
        }
        Product recentProduct;
        try {
            Date datetime = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            System.out.println(datetime.toString());
            recentProduct = productRepository.findLatestBySkuAndDate(sku, datetime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }  
        return recentProduct;
    }

    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public List<Trend> getProductTrends() {
        List<String> skuList = productRepository.findSku();

        List<Trend> trends = new ArrayList<>();
        for (String sku : skuList) {
            Trend trend = new Trend();
            trend.setSku(sku);

            List<TimestampPrice> timestampPrices = productRepository.findPriceTrendOfProduct(sku);
            trend.setTimestampPrice(timestampPrices);
            trends.add(trend);
        }

        return trends;
    }
}
