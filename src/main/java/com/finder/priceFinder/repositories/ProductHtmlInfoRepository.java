package com.finder.priceFinder.repositories;

import com.finder.priceFinder.entities.ProductHtmlInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productHtmlInfoRepository")
public interface ProductHtmlInfoRepository extends JpaRepository<ProductHtmlInfo, Long> {
    
}
