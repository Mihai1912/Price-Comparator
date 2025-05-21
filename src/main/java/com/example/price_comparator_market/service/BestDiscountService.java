package com.example.price_comparator_market.service;

import com.example.price_comparator_market.config.AppConfig;
import com.example.price_comparator_market.entity.Discount;
import com.example.price_comparator_market.repository.DiscountRepository;
import com.example.price_comparator_market.service.dto.DiscountDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BestDiscountService {
    private final DiscountRepository discountRepository;

    @Autowired
    private AppConfig appConfig;

    public BestDiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<DiscountDto> getBestDiscounts() {
        List<DiscountDto> bestDiscounts = new ArrayList<>();
        LocalDateTime currentDate = appConfig.getCurrentDate().atStartOfDay();
        List<String> markets = discountRepository.findAllMarkets();

        for (String market : markets) {
            List<Discount> discounts = discountRepository.findAllByMarket(market);
            Discount bestDiscount = null;

            for (Discount discount : discounts) {
                if (discount.getId().getPublicationDate().isAfter(currentDate)) {
                    continue;
                }
                if ((discount.getFromDate().isEqual(currentDate) || discount.getFromDate().isBefore(currentDate)) &&
                (discount.getToDate().isEqual(currentDate) || discount.getToDate().isAfter(currentDate))) {
                    if (bestDiscount == null || discount.getPercentageOfDiscount().compareTo(bestDiscount.getPercentageOfDiscount()) > 0) {
                        bestDiscount = discount;
                    }
                }
            }

            if (bestDiscount != null) {
                DiscountDto discountDto = getDiscountDto(market, bestDiscount);
                bestDiscounts.add(discountDto);
            }
        }

        return bestDiscounts;
    }

    public List<DiscountDto> getTopNBestDiscounts(int n) {
        List<DiscountDto> bestDiscounts = new ArrayList<>();
        LocalDateTime currentDate = appConfig.getCurrentDate().atStartOfDay();
        List<String> markets = discountRepository.findAllMarkets();

        for (String market : markets) {
            List<Discount> topNDiscounts = new ArrayList<>();
            List<Discount> discounts = discountRepository.findAllByMarket(market);
            discounts.sort(
                    (d1, d2) -> d2.getPercentageOfDiscount().compareTo(d1.getPercentageOfDiscount())
            );
            for (Discount discount : discounts) {
                if (discount.getId().getPublicationDate().isAfter(currentDate)) {
                    continue;
                }
                if ((discount.getFromDate().isEqual(currentDate) || discount.getFromDate().isBefore(currentDate)) &&
                (discount.getToDate().isEqual(currentDate) || discount.getToDate().isAfter(currentDate))) {
                    topNDiscounts.add(discount);
                }
            }
            if (topNDiscounts.size() > n) {
                topNDiscounts = topNDiscounts.subList(0, n);
            }
            for (Discount discount : topNDiscounts) {
                DiscountDto discountDto = getDiscountDto(market, discount);
                bestDiscounts.add(discountDto);
            }
        }
        return bestDiscounts;
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
