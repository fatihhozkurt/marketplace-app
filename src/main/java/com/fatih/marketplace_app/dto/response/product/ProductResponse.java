package com.fatih.marketplace_app.dto.response.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(

        UUID id,
        String productName,
        String productDescription,
        BigDecimal productPrice,
        Long stockQuantity
) {
}
