package com.example.price_comparator_market.service;

import com.example.price_comparator_market.config.AppConfig;
import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.repository.DiscountRepository;
import com.example.price_comparator_market.service.dto.DiscountDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NewDiscountService {
    private final DiscountRepository discountRepository;

    @Autowired
    private AppConfig appConfig;

    public NewDiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }


    public List<DiscountDto> getNewDiscounts() {
        LocalDateTime currentTime = appConfig.getCurrentDate().atStartOfDay();
        List<Discount> newDiscounts = discountRepository.getAllByFromDateIsBetween(currentTime.minusDays(1), currentTime);
        List<DiscountDto> newDiscountDtos = new ArrayList<>();
        for (Discount discount : newDiscounts) {
            if (discount.getId().getPublicationDate().isAfter(currentTime)) {
                continue;
            }
            DiscountDto discountDto = getDiscountDto(discount.getId().getMarket(), discount);
            newDiscountDtos.add(discountDto);
        }
        return newDiscountDtos;
    }

    public List<DiscountDto> getNewDiscountsLastNDays(int n) {
        LocalDateTime currentTime = appConfig.getCurrentDate().atStartOfDay();
        List<Discount> newDiscounts = discountRepository.getAllByFromDateIsBetween(currentTime.minusDays(n), currentTime);
        List<DiscountDto> newDiscountDtos = new ArrayList<>();
        for (Discount discount : newDiscounts) {
            if (discount.getId().getPublicationDate().isAfter(currentTime)) {
                continue;
            }
            DiscountDto discountDto = getDiscountDto(discount.getId().getMarket(), discount);
            newDiscountDtos.add(discountDto);
        }
        return newDiscountDtos;
    }

    private static DiscountDto getDiscountDto(String market, Discount bestDiscount) {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setMarket(market);
        discountDto.setPercentageOfDiscount(bestDiscount.getPercentageOfDiscount());
        discountDto.setName(bestDiscount.getName());
        discountDto.setBrand(bestDiscount.getBrand());
        discountDto.setFromDate(bestDiscount.getFromDate());
        discountDto.setToDate(bestDiscount.getToDate());
        discountDto.setPackageQuantity(bestDiscount.getPackageQuantity());
        discountDto.setPackageUnit(bestDiscount.getPackageUnit());
        discountDto.setProductCategory(bestDiscount.getProductCategory());
        return discountDto;
    }
}
