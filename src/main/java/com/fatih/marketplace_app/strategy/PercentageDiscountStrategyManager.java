package com.fatih.marketplace_app.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementation of {@link DiscountStrategyService} that applies a percentage-based discount to the cart price.
 */
@Service
public class PercentageDiscountStrategyManager implements DiscountStrategyService {

    /**
     * Applies a percentage discount to the given cart price.
     *
     * @param cartPrice     the original total price of the cart
     * @param discountValue the discount percentage to be applied
     * @return the final cart price after applying the discount, ensuring it does not go below zero
     */
    @Override
    public BigDecimal applyDiscount(BigDecimal cartPrice, BigDecimal discountValue) {

        BigDecimal discountAmount = cartPrice.multiply(discountValue).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal discountedCartPrice = cartPrice.subtract(discountAmount);

        return discountedCartPrice.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : discountedCartPrice;
    }
}
