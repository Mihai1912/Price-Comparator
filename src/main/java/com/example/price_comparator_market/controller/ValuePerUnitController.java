package com.example.price_comparator_market.controller;

import com.example.price_comparator_market.service.ValuePerUnitService;
import com.example.price_comparator_market.service.dto.ValuePerUnitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/value-per-unit")
public class ValuePerUnitController {

    private final ValuePerUnitService valuePerUnitService;

    public ValuePerUnitController(ValuePerUnitService valuePerUnitService) {
        this.valuePerUnitService = valuePerUnitService;
    }

    @RequestMapping(path = "/get-for-product/{productName}", method = RequestMethod.GET)
    public ResponseEntity<List<ValuePerUnitDto>> getValuePerUnitForProduct(@PathVariable String productName) {
        try {
            List<ValuePerUnitDto> valuePerUnitDtos = valuePerUnitService.getValuePerUnitForProduct(productName);
            return ResponseEntity.ok(valuePerUnitDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}