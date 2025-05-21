package com.example.price_comparator_market.entity;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class DiscountId implements Serializable {
    private String id;
    private String market;
    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

    public DiscountId() {
    }

    public DiscountId(String id, String market, LocalDateTime publicationDate) {
        this.id = id;
        this.market = market;
        this.publicationDate = publicationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountId that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(market, that.market) &&
                Objects.equals(publicationDate, that.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, market, publicationDate);
    }
}
