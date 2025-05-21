package com.example.price_comparator_market.service;


import com.example.price_comparator_market.config.AppConfig;
import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.entity.Product;
import com.example.price_comparator_market.repository.DiscountRepository;
import com.example.price_comparator_market.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BasketHelperService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(BasketHelperService.class);

    @Autowired
    private AppConfig appConfig;

    public BasketHelperService(DiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    public JSONObject getBestList(List<String> products) throws JSONException {
        Map<String, List<String>> productMap = new HashMap<>();

        for (String product : products){
            List<Product> productList = productRepository.findAllByName(product);
            List<Discount> discountList = discountRepository.findAllByName(product);

            Map<String, BigDecimal> marketPrices = new HashMap<>();
            for (Product p : productList) {
                if (p.getId().getPublicationDate().isAfter(appConfig.getCurrentDate().atStartOfDay())) {
                    continue;
                }
                String market = p.getId().getMarket();
                BigDecimal price = p.getPrice();
                if (marketPrices.containsKey(market)) {
                    marketPrices.put(market, marketPrices.get(market).add(price));
                } else {
                    marketPrices.put(market, price);
                }
            }

            for (Discount d : discountList) {
                if (d.getId().getPublicationDate().isAfter(appConfig.getCurrentDate().atStartOfDay())) {
                    continue;
                }
                String market = d.getId().getMarket();
                if (d.getFromDate().isAfter(appConfig.getCurrentDate().atStartOfDay()) || d.getToDate().isBefore(appConfig.getCurrentDate().atStartOfDay())) {
                    continue;
                }
                BigDecimal percentage = d.getPercentageOfDiscount();
                BigDecimal discountAmount = marketPrices.get(market).multiply(percentage).divide(BigDecimal.valueOf(100));
                marketPrices.put(market, marketPrices.get(market).subtract(discountAmount));
            }

            String bestMarket = null;
            BigDecimal bestPrice = null;

            for (Map.Entry<String, BigDecimal> entry : marketPrices.entrySet()) {
                String market = entry.getKey();
                BigDecimal price = entry.getValue();

                if (bestPrice == null || price.compareTo(bestPrice) < 0) {
                    bestPrice = price;
                    bestMarket = market;
                }
            }

            if (bestMarket != null) {
                if (productMap.containsKey(bestMarket)) {
                    productMap.get(bestMarket).add(product);
                } else {
                    List<String> productListForMarket = new ArrayList<>();
                    productListForMarket.add(product);
                    productMap.put(bestMarket, productListForMarket);
                }
            }
        }

        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, List<String>> entry : productMap.entrySet()) {
            String market = entry.getKey();
            List<String> productList = entry.getValue();
            jsonObject.put(market, productList);
        }

        return jsonObject;
    }
}
