package com.example.price_comparator_market.controller;

import com.example.price_comparator_market.service.CustomPriceAlertService;
import com.example.price_comparator_market.service.dto.UserAlertDto;
import com.example.price_comparator_market.service.dto.UserAlertRespons;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/price-alert")
public class CustomProceAlertController {
    private final CustomPriceAlertService customPriceAlertService;

    public CustomProceAlertController(CustomPriceAlertService customPriceAlertService) {
        this.customPriceAlertService = customPriceAlertService;
    }

    @RequestMapping(path = "/set-alert", method = RequestMethod.POST)
    public ResponseEntity<UserAlertDto> setPriceAlert(@RequestBody UserAlertDto userAlertDto) {
        UserAlertDto createdUserAlert = customPriceAlertService.setPriceAlert(userAlertDto);
        return ResponseEntity.ok(createdUserAlert);
    }

    @RequestMapping(path = "/check-alerts-for/{username}", method = RequestMethod.GET)
    public ResponseEntity<List<UserAlertRespons>> checkAlertsFor(@PathVariable("username") String username) {
        List<UserAlertRespons> userAlerts = customPriceAlertService.checkAlertsFor(username);
        return ResponseEntity.ok(userAlerts);
    }
}
