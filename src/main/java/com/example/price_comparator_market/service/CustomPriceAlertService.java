package com.example.price_comparator_market.service;

import com.example.price_comparator_market.config.AppConfig;
import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.entity.Product;
import com.example.price_comparator_market.entity.UserAlert;
import com.example.price_comparator_market.repository.DiscountRepository;
import com.example.price_comparator_market.repository.ProductRepository;
import com.example.price_comparator_market.repository.UserAlertRepository;
import com.example.price_comparator_market.service.dto.UserAlertDto;
import com.example.price_comparator_market.service.dto.UserAlertRespons;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CustomPriceAlertService {
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final UserAlertRepository userAlertRepository;

    @Autowired
    private AppConfig appConfig;

    public static final Logger log = LoggerFactory.getLogger(CustomPriceAlertService.class);

    public CustomPriceAlertService(ProductRepository productRepository, DiscountRepository discountRepository, UserAlertRepository userAlertRepository) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
        this.userAlertRepository = userAlertRepository;
    }


    public UserAlertDto setPriceAlert(UserAlertDto userAlertDto) {
        UserAlert userAlert = new UserAlert();
        userAlert.setUsername(userAlertDto.getUsername());
        userAlert.setProductName(userAlertDto.getProductName());
        userAlert.setPrice(userAlertDto.getPrice());

        userAlertRepository.save(userAlert);

        return userAlertDto;
    }

    public List<UserAlertRespons> checkAlertsFor(String username) {
        List<UserAlert> userAlerts = userAlertRepository.findByUsername(username);
        List<UserAlertRespons> userAlertRespons = new ArrayList<>();

        for (UserAlert userAlert : userAlerts) {
            UserAlertRespons userAlertRes = new UserAlertRespons();
            userAlertRes.setUsername(userAlert.getUsername());
            userAlertRes.setProductName(userAlert.getProductName());
            userAlertRes.setMarketPrices(new HashMap<>());
            String productName = userAlert.getProductName();
            BigDecimal price = userAlert.getPrice();
            LocalDateTime currentDate = appConfig.getCurrentDate().atStartOfDay();

            int publicationDataDay = (currentDate.getDayOfMonth()/8)*8;
            int publicationDataMonth = currentDate.getMonthValue();
            int publicationDataYear = currentDate.getYear();
            if (publicationDataDay == 0) {
                publicationDataDay = 1;
            }

            LocalDateTime publicationDate = LocalDateTime.of(publicationDataYear, publicationDataMonth, publicationDataDay, 0, 0);

            List<Product> products = productRepository.findAllByName(productName).stream().filter(
                    product -> product.getId().getPublicationDate().isEqual(publicationDate)
            ).toList();

            List<Discount> discounts = discountRepository.findAllByName(productName)
                    .stream().filter(
                    discount -> (discount.getFromDate().isBefore(currentDate) || discount.getFromDate().isEqual(currentDate))
                            && (discount.getToDate().isAfter(currentDate) || discount.getToDate().isEqual(currentDate))).toList();

            for (Product product : products) {
                for (Discount discount : discounts) {
                    if (!product.getId().getMarket().equals(discount.getId().getMarket()))
                        continue;
                    BigDecimal productPrice = product.getPrice();
                    BigDecimal discountPercentage = discount.getPercentageOfDiscount();
                    BigDecimal discountAmount = productPrice.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
                    BigDecimal discountedPrice = productPrice.subtract(discountAmount);
                    if (discountedPrice.compareTo(price) < 0) {
                        userAlertRes.getMarketPrices().put(product.getId().getMarket(), discountedPrice);
                    }
                }
            }
            if (userAlertRes.getMarketPrices() != null && !userAlertRes.getMarketPrices().isEmpty()) {
                userAlertRespons.add(userAlertRes);
            }
        }
        return userAlertRespons;
    }
}
