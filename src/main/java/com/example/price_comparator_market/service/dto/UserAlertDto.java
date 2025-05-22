package com.example.price_comparator_market.service.dto;

import java.math.BigDecimal;

public class UserAlertDto {
    private String username;
    private String productName;
    private BigDecimal price;

    public UserAlertDto() {
    }

    public UserAlertDto(String username, String productName, BigDecimal price) {
        this.username = username;
        this.productName = productName;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
