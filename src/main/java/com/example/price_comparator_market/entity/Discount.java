package com.example.price_comparator_market.entity;

import jakarta.persistence.*;
import jakarta.persistence.EmbeddedId;
import org.hibernate.annotations.CollectionId;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
public class Discount {
    @EmbeddedId
    private DiscountId id;
    private String name;
    private String brand;
    @Column(name = "package_quantity")
    private BigDecimal packageQuantity;
    @Column(name = "package_unit")
    private String packageUnit;
    @Column(name = "product_category")
    private String productCategory;
    @Column(name = "from_date")
    private LocalDateTime fromDate;
    @Column(name = "to_date")
    private LocalDateTime toDate;
    @Column(name = "percentage_of_discount")
    private BigDecimal percentageOfDiscount;

    public Discount() {}

    public DiscountId getId() {
        return id;
    }

    public void setId(DiscountId id) {
        this.id = id;
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
}
