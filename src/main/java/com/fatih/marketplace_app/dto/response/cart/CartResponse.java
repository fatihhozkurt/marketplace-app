package com.fatih.marketplace_app.dto.response.cart;

import java.math.BigDecimal;
import java.util.UUID;

public record CartResponse(

        BigDecimal cartPrice,
        UUID userId,
        UUID cartId,
        UUID campaignId
) {
}
