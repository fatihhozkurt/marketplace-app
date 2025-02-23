package com.fatih.marketplace_app.dto.request.cartItem;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateCartItemRequest(

        @NotNull
        UUID cartItemId,

        @Positive
        Integer productQuantity,

        @Positive
        @Digits(integer = 12, fraction = 2)
        BigDecimal cartItemPrice
) {
}
