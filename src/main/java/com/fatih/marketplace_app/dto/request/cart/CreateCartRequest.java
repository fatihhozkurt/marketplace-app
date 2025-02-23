package com.fatih.marketplace_app.dto.request.cart;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCartRequest(
        @NotNull
        UUID userId
) {
}
