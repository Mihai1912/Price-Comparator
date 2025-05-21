package com.example.price_comparator_market.repository;

import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.entity.Product;
import com.example.price_comparator_market.entity.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
    List<Product> findAllByName(String product);
    @Query("SELECT DISTINCT p.id.market FROM Product p")
    List<String> findAllMarkets();
    @Query("SELECT p FROM Product p WHERE p.id.market = :market AND p.name = :product")
    Product findProductByNameAndMarket(String product, String market);
    @Query("SELECT p FROM Product p WHERE p.name = :product AND p.id.publicationDate = :publicationDate")
    List<Product> findAllByPublicationDateAndName(String product, LocalDateTime publicationDate);
}
