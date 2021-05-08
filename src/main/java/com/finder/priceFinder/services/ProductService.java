package com.finder.priceFinder.services;

import com.finder.priceFinder.entities.ProductHtmlInfo;

public interface ProductService {
    
    public ProductHtmlInfo crawlAndSave(String url, String sku);

}
