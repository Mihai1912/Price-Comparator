package com.example.price_comparator_market.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_alerts")
public class UserAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @Column(name = "product_name")
    private String productName;
    private BigDecimal price;

    public UserAlert() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
