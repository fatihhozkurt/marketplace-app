package com.fatih.marketplace_app.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FixedDiscountStrategyManager implements DiscountStrategyService {
    @Override
    public BigDecimal applyDiscount(BigDecimal cartPrice, BigDecimal discountValue) {

        BigDecimal discountedCartPrice = cartPrice.subtract(discountValue);

        return discountedCartPrice.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : discountedCartPrice;
    }
}
