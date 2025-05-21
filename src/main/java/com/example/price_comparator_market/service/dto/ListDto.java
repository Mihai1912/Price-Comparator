package com.example.price_comparator_market.service.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListDto {
    private List<String> products;

    public ListDto() {
    }

    public ListDto(List<String> products) {
        this.products = products;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
