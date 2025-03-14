package com.fatih.marketplace_app.dto.request.cart;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateCartRequest(
        @NotNull
        UUID cartId,

        @PositiveOrZero
        @Digits(integer = 12, fraction = 2)
        BigDecimal cartPrice
) {
}
