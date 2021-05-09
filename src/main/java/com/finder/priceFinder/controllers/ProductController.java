package com.finder.priceFinder.controllers;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.HashAttributeSet;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.ProductHtmlInfo;
import com.finder.priceFinder.entities.Trend;
import com.finder.priceFinder.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;

    @GetMapping() 
    public ResponseEntity<?> getProducts(@RequestParam(required = false, name = "url") String url, @RequestParam(required = false, name = "sku") String sku, @RequestParam(required = false, name = "date") String date) {
        Instant start = Instant.now();
        HashMap<String, Object> respMap = new HashMap<>();
        
        if (url == null && sku == null && date == null) {
            logger.info("Fetching all the products");
            List<Product> products = new ArrayList<>(); 
            products = productService.getAllProducts();
            respMap.put("data", products);
        } else if (date == null){
            logger.info("Fetching the product detail from the URL/SKU");
            Product product = productService.getProductDetails(url, sku);
            respMap.put("data", product);
        } else {
            logger.info("Fetching latest product by time");
            Product product = productService.getLastUpdatedProductDetails(url, sku, date);
            respMap.put("data", product);
        }

        String strTime = getTimeTaken(start);
        logger.info("/products time taken: {} ",  strTime);
        respMap.put("timeTaken",strTime);
        return new ResponseEntity<>(respMap, HttpStatus.OK);
    }

    @GetMapping("/html")
    public ResponseEntity<?> getHtml(@RequestParam(required = false, name = "url") String url, @RequestParam(required = false, name = "sku") String sku){
        Instant start = Instant.now();
        HashMap<String, Object> respMap = new HashMap<>();
        if (url == null && sku == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Query Params missing");
        }
        logger.info("Fetching html from the URL/SKU");
        ProductHtmlInfo productHtmlInfo = productService.crawlAndSave(url, sku);
        respMap.put("data", productHtmlInfo.getHtml());

        String strTime = getTimeTaken(start);
        logger.info("/products/html time taken: {} ",  strTime);
        respMap.put("timeTaken", strTime);
        return new ResponseEntity<>(respMap, HttpStatus.OK);
    }

    @GetMapping("/trends")
    public ResponseEntity<?> getProductTrends() {
        Instant start = Instant.now();
        HashMap<String, Object> respMap = new HashMap<>();

        List<Trend> trends = productService.getProductTrends();
        respMap.put("data", trends);

        String strTime = getTimeTaken(start);
        logger.info("/products/trends time taken: {} ",  strTime);
        respMap.put("timeTaken", strTime);
        return new ResponseEntity<>(respMap, HttpStatus.OK);
    }

    public String getTimeTaken(Instant start) {
        long time = Duration.between(start, Instant.now()).toMillis();
        String strTime = String.valueOf(time) + " ms";
        return strTime;
    }
}
