package com.example.price_comparator_market.service;

import com.example.price_comparator_market.config.AppConfig;
import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.entity.Product;
import com.example.price_comparator_market.repository.DiscountRepository;
import com.example.price_comparator_market.repository.ProductRepository;
import com.example.price_comparator_market.service.dto.ValuePerUnitDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ValuePerUnitService {
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    private final static Logger log = LoggerFactory.getLogger(ValuePerUnitService.class);

    @Autowired
    private AppConfig appConfig;

    public ValuePerUnitService(ProductRepository productRepository, DiscountRepository discountRepository) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

    public List<ValuePerUnitDto> getValuePerUnitForProduct(String productName) {

        LocalDateTime currentDateTime = appConfig.getCurrentDate().atStartOfDay();
        List<ValuePerUnitDto> valuePerUnitDtos = new ArrayList<>();

        int publicationDataDay = (currentDateTime.getDayOfMonth() / 8) * 8;
        int publicationDataMonth = currentDateTime.getMonthValue();
        int publicationDataYear = currentDateTime.getYear();

        if (publicationDataDay == 0) {
            publicationDataDay = 1;
        }

        LocalDateTime publicationDateTime = LocalDateTime.of(publicationDataYear, publicationDataMonth, publicationDataDay, 0, 0);

        List<Product> products = productRepository.findAllByName(productName).stream().filter(
                product -> product.getId().getPublicationDate().isEqual(publicationDateTime)).toList();

        List<Discount> discounts = discountRepository.findAllByName(productName).stream().filter(
                        discount -> (discount.getFromDate().isBefore(currentDateTime) || discount.getFromDate().isEqual(currentDateTime))
                        && (discount.getToDate().isAfter(currentDateTime) || discount.getToDate().isEqual(currentDateTime))).toList();

        for (Product product : products) {
            for (Discount discount : discounts) {
                if (!product.getId().getMarket().equals(discount.getId().getMarket()))
                    continue;
                BigDecimal productPrice = product.getPrice();
                BigDecimal discountPercentage = discount.getPercentageOfDiscount();
                BigDecimal discountAmount = productPrice.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
                BigDecimal discountedPrice = productPrice.subtract(discountAmount);

                Product finalProduct = new Product();
                finalProduct.setId(product.getId());
                finalProduct.setPrice(discountedPrice);
                finalProduct.setName(product.getName());
                finalProduct.setBrand(product.getBrand());
                finalProduct.setPackageUnit(product.getPackageUnit());
                finalProduct.setCategory(product.getCategory());
                finalProduct.setCurrency(product.getCurrency());
                finalProduct.setPackageQuantity(product.getPackageQuantity());

                BigDecimal valuePerUnit = getPricePerUnit(finalProduct);

                log.info(finalProduct.getPackageUnit());

                if (finalProduct.getPackageUnit().equals("buc")){
                    ValuePerUnitDto valuePerUnitDto = new ValuePerUnitDto();
                    valuePerUnitDto.setMarket(product.getId().getMarket());
                    valuePerUnitDto.setValuePerUnit(valuePerUnit);
                    valuePerUnitDto.setUnit("price per buc");
                    valuePerUnitDtos.add(valuePerUnitDto);
                } else if (finalProduct.getPackageUnit().equals("role")){
                    ValuePerUnitDto valuePerUnitDto = new ValuePerUnitDto();
                    valuePerUnitDto.setMarket(product.getId().getMarket());
                    valuePerUnitDto.setValuePerUnit(valuePerUnit);
                    valuePerUnitDto.setUnit("price per role");
                    valuePerUnitDtos.add(valuePerUnitDto);
                } else if (finalProduct.getPackageUnit().equals("ml")){
                    ValuePerUnitDto valuePerUnitDto = new ValuePerUnitDto();
                    valuePerUnitDto.setMarket(product.getId().getMarket());
                    valuePerUnitDto.setValuePerUnit(valuePerUnit);
                    valuePerUnitDto.setUnit("price per l");
                    valuePerUnitDtos.add(valuePerUnitDto);
                } else if (finalProduct.getPackageUnit().equals("g")){
                    ValuePerUnitDto valuePerUnitDto = new ValuePerUnitDto();
                    valuePerUnitDto.setMarket(product.getId().getMarket());
                    valuePerUnitDto.setValuePerUnit(valuePerUnit);
                    valuePerUnitDto.setUnit("price per kg");
                    valuePerUnitDtos.add(valuePerUnitDto);
                } else {
                    ValuePerUnitDto valuePerUnitDto = new ValuePerUnitDto();
                    valuePerUnitDto.setMarket(product.getId().getMarket());
                    valuePerUnitDto.setValuePerUnit(valuePerUnit);
                    valuePerUnitDto.setUnit("price per " + finalProduct.getPackageUnit());
                    valuePerUnitDtos.add(valuePerUnitDto);
                }
            }
        }

        return valuePerUnitDtos;
    }

    public static BigDecimal getPricePerUnit(Product product) {
        BigDecimal quantity = product.getPackageQuantity();
        String unit = product.getPackageUnit().toLowerCase();
        BigDecimal price = product.getPrice();

        switch (unit) {
            case "ml":
                quantity = quantity.divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);
                unit = "l";
                break;
            case "g":
                quantity = quantity.divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);
                unit = "kg";
                break;
            case "l":
            case "kg":
            case "buc":
            case "role":
                break;
            default:
                throw new IllegalArgumentException("Unitate necunoscută: " + unit);
        }

        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Cantitate pachet nu poate fi zero.");
        }

        return price.divide(quantity, 6, RoundingMode.HALF_UP);
    }

}
