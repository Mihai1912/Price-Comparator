package com.example.price_comparator_market.controller;

import com.example.price_comparator_market.service.PriceHistoryService;
import com.example.price_comparator_market.service.dto.ProductHistoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price-history")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @RequestMapping(path = "/getPriceHistory/{n}/days/{productName}", method = RequestMethod.GET)
    public ResponseEntity<List<ProductHistoryDto>> getPriceHistoryNDays(@PathVariable int n, @PathVariable String productName) {
        try {
            List<ProductHistoryDto> productHistory = priceHistoryService.getPriceHistoryNDays(n, productName);
            return ResponseEntity.status(HttpStatus.OK).body(productHistory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
