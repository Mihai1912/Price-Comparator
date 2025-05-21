package com.example.price_comparator_market.service;

import com.example.price_comparator_market.config.AppConfig;
import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.entity.Product;
import com.example.price_comparator_market.repository.DiscountRepository;
import com.example.price_comparator_market.repository.ProductRepository;
import com.example.price_comparator_market.service.dto.ProductHistoryDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PriceHistoryService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    private static final Logger logger = LoggerFactory.getLogger(PriceHistoryService.class);

    @Autowired
    private AppConfig appConfig;

    public PriceHistoryService(ProductRepository productRepository, DiscountRepository discountRepository) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }


    public List<ProductHistoryDto> getPriceHistoryNDays(int n, String productName) {
        List<ProductHistoryDto> returnList = new ArrayList<>();
        Map<LocalDateTime, List<ProductHistoryDto>> productMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int day = (appConfig.getCurrentDate().atStartOfDay().minusDays(i).getDayOfMonth()/8)*8;
            int month = appConfig.getCurrentDate().atStartOfDay().minusDays(i).getMonthValue();
            int year = appConfig.getCurrentDate().atStartOfDay().minusDays(i).getYear();
            if (day == 0) {
                day = 1;
            }
            LocalDate date = LocalDate.of(year, month, day);

            List<Product> productsByDate = productRepository.findAllByPublicationDateAndName(productName, date.atStartOfDay());
            List<ProductHistoryDto> productHistoryDtos = new ArrayList<>();

            for (Product product : productsByDate) {
                ProductHistoryDto productHistoryDto = new ProductHistoryDto(
                        product.getName(),
                        product.getBrand(),
                        product.getCategory(),
                        product.getPrice(),
                        product.getId().getMarket(),
                        appConfig.getCurrentDate().atStartOfDay().minusDays(i)
                );
                Discount discount = discountRepository.findByPublicationDateNameAndMarket(productName, appConfig.getCurrentDate().atStartOfDay().minusDays(i), product.getId().getMarket());
                if (discount != null) {
                    BigDecimal price = product.getPrice();
                    BigDecimal percentage = discount.getPercentageOfDiscount();
                    BigDecimal discountAmount = price.multiply(percentage).divide(BigDecimal.valueOf(100));
                    BigDecimal discountedPrice = price.subtract(discountAmount);
                    productHistoryDto.setPrice(discountedPrice);
                    productHistoryDtos.add(productHistoryDto);
                } else {
                    productHistoryDtos.add(productHistoryDto);
                }
            }

            if (productsByDate != null && !productsByDate.isEmpty()) {
                productMap.put(appConfig.getCurrentDate().atStartOfDay().minusDays(i), productHistoryDtos);
            }
            returnList.addAll(productHistoryDtos);
        }

        return returnList;
    }
}
