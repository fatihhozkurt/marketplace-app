package com.fatih.marketplace_app.dto.response.campaign;

import java.math.BigDecimal;
import java.util.UUID;

public record ApplyCampaignResponse(

        UUID cartId,
        BigDecimal discountValue,
        BigDecimal discountedCartPrice
) {
}
