package com.fatih.marketplace_app.dto.response.wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(

        UUID walletId,
        UUID userId,
        String firstName,
        String lastName,
        BigDecimal balance
) {
}
