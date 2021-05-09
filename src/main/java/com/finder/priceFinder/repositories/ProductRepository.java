package com.finder.priceFinder.repositories;

import java.util.Date;
import java.util.List;

import com.finder.priceFinder.entities.Product;
import com.finder.priceFinder.entities.TimestampPrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, String>{
    List<Product> findBySku(String sku);

    @Query(nativeQuery = true, 
    value = "SELECT * FROM products WHERE sku = (:sku) order by created_at limit 1")
    Product findBySkuOrderByCreatedAt(String sku);

    @Query(nativeQuery = true, 
    value = "SELECT * FROM products WHERE sku = (:sku) and created_at <= (:crawledAt) order by created_at limit 1")
    Product findLatestBySkuAndDate(String sku, Date crawledAt);

    @Query(nativeQuery = true, 
    value = "select created_at as timestamp, offer_price as price from products where sku = (:sku) order by created_at desc")
    List<TimestampPrice> findPriceTrendOfProduct(String sku);

    @Query(nativeQuery = true, 
    value = "SELECT distinct(sku) FROM products")
    List<String> findSku();
}