package com.fatih.marketplace_app.dto.response.cartItem;

import com.fatih.marketplace_app.dto.response.product.ProductResponse;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(

        UUID cartItemId,

        BigDecimal cartItemPrice,

        ProductResponse productResponse,

        Integer productQuantity
) {
}
