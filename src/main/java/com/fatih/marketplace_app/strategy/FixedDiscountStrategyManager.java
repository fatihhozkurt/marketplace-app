package com.fatih.marketplace_app.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Implementation of {@link DiscountStrategyService} that applies a fixed discount to the cart price.
 */
@Service
public class FixedDiscountStrategyManager implements DiscountStrategyService {

    /**
     * Applies a fixed discount to the given cart price.
     *
     * @param cartPrice     the original total price of the cart
     * @param discountValue the fixed discount amount to be deducted
     * @return the final cart price after applying the discount, ensuring it does not go below zero
     */
    @Override
    public BigDecimal applyDiscount(BigDecimal cartPrice, BigDecimal discountValue) {

        BigDecimal discountedCartPrice = cartPrice.subtract(discountValue);

        return discountedCartPrice.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : discountedCartPrice;
    }
}
