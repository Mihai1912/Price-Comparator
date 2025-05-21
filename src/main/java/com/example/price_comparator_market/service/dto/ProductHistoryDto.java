package com.example.price_comparator_market.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductHistoryDto {
    private String name;
    private String brand;
    private String category;
    private BigDecimal price;
    private String market;
    private LocalDateTime dateTime;

    public ProductHistoryDto(String name, String brand, String category, BigDecimal price, String market, LocalDateTime dateTime) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.market = market;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
