package com.example.price_comparator_market.controller;

import com.example.price_comparator_market.service.BestDiscountService;
import com.example.price_comparator_market.service.dto.DiscountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/best-discount")
public class BestDiscountController{
    private final BestDiscountService bestDiscountService;

    protected BestDiscountController(BestDiscountService bestDiscountService) {
        this.bestDiscountService = bestDiscountService;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<DiscountDto>> getBestDiscounts(){
        try {
            List<DiscountDto> bestDiscounts = bestDiscountService.getBestDiscounts();
            return new ResponseEntity<>(bestDiscounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get/top/{n}", method = RequestMethod.GET)
    public ResponseEntity<List<DiscountDto>> getTopNBestDiscounts(@PathVariable int n){
        try {
            List<DiscountDto> bestDiscounts = bestDiscountService.getTopNBestDiscounts(n);
            return new ResponseEntity<>(bestDiscounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
