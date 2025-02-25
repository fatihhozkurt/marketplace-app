package com.fatih.marketplace_app.strategy;

import java.math.BigDecimal;

/**
 * Interface for applying discount strategies to cart prices.
 */
public interface DiscountStrategyService {

    /**
     * Applies a discount to the given cart price based on the specified discount value.
     *
     * @param cartPrice     The original price of the cart.
     * @param discountValue The discount value to be applied.
     * @return The final price after applying the discount.
     */
    BigDecimal applyDiscount(BigDecimal cartPrice, BigDecimal discountValue);
}
