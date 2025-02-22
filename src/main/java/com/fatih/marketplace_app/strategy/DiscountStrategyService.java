package com.fatih.marketplace_app.strategy;

import java.math.BigDecimal;

public interface DiscountStrategyService {

    BigDecimal applyDiscount(BigDecimal cartPrice, BigDecimal discountValue);

}
