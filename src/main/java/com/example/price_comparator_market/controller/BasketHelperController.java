package com.example.price_comparator_market.controller;

import com.example.price_comparator_market.service.BasketHelperService;
import com.example.price_comparator_market.service.dto.ListDto;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/basket-helper")
public class BasketHelperController {
    private final BasketHelperService basketHelperService;

    private static final Logger logger = LoggerFactory.getLogger(BasketHelperController.class);

    public BasketHelperController(BasketHelperService basketHelperService) {
        this.basketHelperService = basketHelperService;
    }

    @RequestMapping(path = "/get/best-list", method = RequestMethod.GET)
    public ResponseEntity<String> getBestList(@RequestBody ListDto listDto) {
        try{
            JSONObject returnedJson = basketHelperService.getBestList(listDto.getProducts());

            logger.info(returnedJson.toString());

            return ResponseEntity.status(200).body(returnedJson.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
