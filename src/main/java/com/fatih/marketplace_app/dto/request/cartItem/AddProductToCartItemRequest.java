package com.fatih.marketplace_app.dto.request.cartItem;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddProductToCartItemRequest(

        @NotNull UUID cartItemId,

        @NotNull UUID productId
) {
}
