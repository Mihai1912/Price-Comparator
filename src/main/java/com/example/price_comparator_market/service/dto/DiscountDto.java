package com.example.price_comparator_market.service.dto;


import jakarta.persistence.Column;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DiscountDto {
    private String market;
    private String name;
    private String brand;
    private BigDecimal packageQuantity;
    private String packageUnit;
    private String productCategory;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private BigDecimal percentageOfDiscount;

    public DiscountDto() {
    }

    public DiscountDto(String market, String name, String brand, BigDecimal packageQuantity, String packageUnit, String productCategory, LocalDateTime fromDate, LocalDateTime toDate, BigDecimal percentageOfDiscount) {
        this.market = market;
        this.name = name;
        this.brand = brand;
        this.packageQuantity = packageQuantity;
        this.packageUnit = packageUnit;
        this.productCategory = productCategory;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
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

    public BigDecimal getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(BigDecimal packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(BigDecimal percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("market", market);
        jsonObject.put("name", name);
        jsonObject.put("brand", brand);
        jsonObject.put("packageQuantity", packageQuantity);
        jsonObject.put("packageUnit", packageUnit);
        jsonObject.put("productCategory", productCategory);
        jsonObject.put("fromDate", fromDate);
        jsonObject.put("toDate", toDate);
        jsonObject.put("percentageOfDiscount", percentageOfDiscount);
        return jsonObject;
    }
}
