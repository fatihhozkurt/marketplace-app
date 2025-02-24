package com.fatih.marketplace_app.dto.response.order;

import com.fatih.marketplace_app.dto.response.address.AddressResponse;
import com.fatih.marketplace_app.dto.response.cartItem.CartItemResponse;
import com.fatih.marketplace_app.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(

        UUID orderId,
        String orderNumber,
        OrderStatus orderStatus,
        String firstName,
        String lastName,
        List<CartItemResponse> cartItemResponses,
        BigDecimal finalPrice,
        AddressResponse addressResponse
) {

}
