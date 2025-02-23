package com.fatih.marketplace_app.dto.request.cart;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateCartRequest(
        @NotNull
        UUID cartId,

        BigDecimal cartPrice
) {
}
