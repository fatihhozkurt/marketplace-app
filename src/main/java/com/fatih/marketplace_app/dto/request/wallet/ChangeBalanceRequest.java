package com.fatih.marketplace_app.dto.request.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record ChangeBalanceRequest(

        @NotNull
        UUID walletId,

        @NotNull
        @PositiveOrZero
        BigDecimal amount
) {
}
