package com.example.price_comparator_market.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@Component
public class AppConfig {

    @Value("${app.current-date}")
    private String currentDateString;

    @Getter
    private LocalDate currentDate;

    @PostConstruct
    public void init() {
        this.currentDate = LocalDate.parse(currentDateString);
    }
}

