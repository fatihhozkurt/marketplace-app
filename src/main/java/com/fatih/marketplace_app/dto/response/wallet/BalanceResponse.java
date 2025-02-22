package com.fatih.marketplace_app.dto.response.wallet;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal balance
) {
}
