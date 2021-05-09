package com.finder.priceFinder.services;

import java.util.List;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.ProductHtmlInfo;
import com.finder.priceFinder.entities.Trend;

public interface ProductService {
    
    public ProductHtmlInfo crawlAndSave(String url, String sku);
    public Product getProductDetails(String url, String sku);
    public List<Product> getAllProducts();
    public Product getLastUpdatedProductDetails(String url, String sku, String date);
    public List<Trend> getProductTrends();
}
