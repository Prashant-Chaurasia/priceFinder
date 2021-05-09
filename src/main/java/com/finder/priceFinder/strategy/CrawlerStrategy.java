package com.finder.priceFinder.strategy;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.ProductHtmlInfo;

public interface CrawlerStrategy {
    
    String getUrlToCrawl(String url, String sku);
    String getSkuFromUrl(String url);
    Product getProductDetails(ProductHtmlInfo productHtmlInfo);
}
