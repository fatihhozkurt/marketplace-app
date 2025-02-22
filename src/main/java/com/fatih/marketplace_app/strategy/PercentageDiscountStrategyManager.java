package com.fatih.marketplace_app.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PercentageDiscountStrategyManager implements DiscountStrategyService {

    @Override
    public BigDecimal applyDiscount(BigDecimal cartPrice, BigDecimal discountValue) {

        BigDecimal discountAmount = cartPrice.multiply(discountValue).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal discountedCartPrice = cartPrice.subtract(discountAmount);

        return discountedCartPrice.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : discountedCartPrice;
    }
}
