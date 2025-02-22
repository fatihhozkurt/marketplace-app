package com.fatih.marketplace_app.dto.request.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record LoadBalanceRequest(

        @NotNull
        @Positive
        BigDecimal amount,

        @NotNull
        UUID walletId
) {
}
