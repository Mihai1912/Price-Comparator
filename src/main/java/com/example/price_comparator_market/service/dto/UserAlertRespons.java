package com.example.price_comparator_market.service.dto;

import java.math.BigDecimal;
import java.util.Map;

public class UserAlertRespons {
    private String username;
    private String productName;
    private Map<String, BigDecimal> marketPrices;

    public UserAlertRespons() {}

    public UserAlertRespons(String username, String productName, Map<String, BigDecimal> marketPrices) {
        this.username = username;
        this.productName = productName;
        this.marketPrices = marketPrices;
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

    public Map<String, BigDecimal> getMarketPrices() {
        return marketPrices;
    }

    public void setMarketPrices(Map<String, BigDecimal> marketPrices) {
        this.marketPrices = marketPrices;
    }
}
