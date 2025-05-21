package com.example.price_comparator_market.controller;

import com.example.price_comparator_market.service.NewDiscountService;
import com.example.price_comparator_market.service.dto.DiscountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/new-discount")
public class NewDiscountController {
    private final NewDiscountService newDiscountService;

    public NewDiscountController(NewDiscountService newDiscountService) {
        this.newDiscountService = newDiscountService;
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<DiscountDto>> getNewDiscount() {
        try {
            List<DiscountDto> newDiscounts = newDiscountService.getNewDiscounts();
            return ResponseEntity.status(HttpStatus.OK).body(newDiscounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(path = "/get/last/{n}/days", method = RequestMethod.GET)
    public ResponseEntity<List<DiscountDto>> getNewDiscountsLastNDays(@PathVariable int n) {
        try {
            List<DiscountDto> newDiscounts = newDiscountService.getNewDiscountsLastNDays(n);
            return ResponseEntity.status(HttpStatus.OK).body(newDiscounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
