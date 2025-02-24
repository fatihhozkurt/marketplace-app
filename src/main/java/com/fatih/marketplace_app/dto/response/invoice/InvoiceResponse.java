package com.fatih.marketplace_app.dto.response.invoice;

import com.fatih.marketplace_app.dto.response.address.AddressResponse;
import com.fatih.marketplace_app.dto.response.cartItem.CartItemResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record InvoiceResponse(

        UUID invoiceId,
        String invoiceNumber,
        String invoiceDate,
        String orderNumber,
        String firstName,
        String lastName,
        BigDecimal finalPrice,
        List<CartItemResponse> cartItemResponses,
        AddressResponse addressResponse
) {
}
