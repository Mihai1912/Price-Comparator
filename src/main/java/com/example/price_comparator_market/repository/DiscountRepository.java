package com.example.price_comparator_market.repository;

import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.entity.DiscountId;
import com.example.price_comparator_market.service.dto.DiscountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, DiscountId> {
    @Query("SELECT DISTINCT d.id.market FROM Discount d")
    List<String> findAllMarkets();
    @Query("SELECT d FROM Discount d WHERE d.id.market = :market")
    List<Discount> findAllByMarket(String market);
    List<Discount> getAllByFromDateIsBetween(LocalDateTime fromDate, LocalDateTime toDate);
    List<Discount> findAllByName(String name);

    @Query("SELECT d FROM Discount d WHERE d.id.market = :market AND d.name = :productName AND :localDateTime BETWEEN d.fromDate AND d.toDate")
    Discount findByPublicationDateNameAndMarket(String productName, LocalDateTime localDateTime, String market);
}
