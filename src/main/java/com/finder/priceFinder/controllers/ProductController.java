package com.finder.priceFinder.controllers;

import java.util.ArrayList;
import java.util.List;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.ProductHtmlInfo;
import com.finder.priceFinder.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping() 
    public ResponseEntity<?> getProducts(@RequestParam(required = false, name = "url") String url, @RequestParam(required = false, name = "sku") String sku, @RequestParam(required = false, name = "date") String date) {
        
        ResponseEntity<?> resp;
        
        if (url == null && sku == null && date == null) {
            List<Product> products = new ArrayList<>(); 
            products = productService.getAllProducts();
            resp = new ResponseEntity<>(products, HttpStatus.OK);
        } else if (date == null){
            Product product = productService.getProductDetails(url, sku);
            resp = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            Product product = productService.getLastUpdatedProductDetails(url, sku, date);
            resp = new ResponseEntity<>(product, HttpStatus.OK);
        }

        return resp;
    }

    @GetMapping("/html")
    public ResponseEntity<?> getHtml(@RequestParam(required = false, name = "url") String url, @RequestParam(required = false, name = "sku") String sku){
        if (url == null && sku == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Query Params missing");
        }
        ProductHtmlInfo resp = productService.crawlAndSave(url, sku);
        return new ResponseEntity<>(resp.getHtml(), HttpStatus.OK);
    }

    @GetMapping("/trends")
    public ResponseEntity<?> getProductTrends() {
        ResponseEntity<?> resp;
        resp = new ResponseEntity<>(productService.getProductTrends(), HttpStatus.OK);
        return resp;
    }
}
