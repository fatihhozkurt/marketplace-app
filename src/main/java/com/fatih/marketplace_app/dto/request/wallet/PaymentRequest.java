package com.fatih.marketplace_app.dto.request.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(

        @NotNull
        UUID walletId,

        @NotNull
        @Positive
        BigDecimal amount
) {
}
