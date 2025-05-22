package com.example.price_comparator_market.service.dto;

import java.math.BigDecimal;

public class ValuePerUnitDto {
    private String market;
    private String unit;
    private BigDecimal valuePerUnit;

    public ValuePerUnitDto() {}

    public ValuePerUnitDto(String market, String unit, BigDecimal valuePerUnit) {
        this.market = market;
        this.unit = unit;
        this.valuePerUnit = valuePerUnit;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getValuePerUnit() {
        return valuePerUnit;
    }

    public void setValuePerUnit(BigDecimal valuePerUnit) {
        this.valuePerUnit = valuePerUnit;
    }
}
