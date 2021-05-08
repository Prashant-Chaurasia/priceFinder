package com.finder.priceFinder.controllers;

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
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok("Testing");
    }

    @GetMapping("/html")
    public ResponseEntity<?> getHtml(@RequestParam(required = false, name = "url") String url, @RequestParam(required = false, name = "sku") String sku){
        if (url == null && sku == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Query Params missing");
        }
        ProductHtmlInfo resp = productService.crawlAndSave(url, sku);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
