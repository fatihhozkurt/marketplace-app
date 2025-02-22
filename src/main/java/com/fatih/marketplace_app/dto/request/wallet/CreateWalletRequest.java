package com.fatih.marketplace_app.dto.request.wallet;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateWalletRequest(
        @NotNull
        UUID userId
) {
}
