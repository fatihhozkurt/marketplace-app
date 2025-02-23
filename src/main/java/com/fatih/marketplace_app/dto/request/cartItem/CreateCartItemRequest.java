package com.fatih.marketplace_app.dto.request.cartItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateCartItemRequest(

        @NotNull
        UUID productId,

        @NotNull
        UUID cartId,

        @Positive
        @NotNull
        Integer productQuantity
) {
}
