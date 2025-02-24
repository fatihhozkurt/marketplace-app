package com.fatih.marketplace_app.dto.request.order;

import com.fatih.marketplace_app.dto.request.address.CreateAddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOrderRequest(

        @NotNull
        UUID cartId,

        @NotNull
        UUID walletId,

        @Valid
        CreateAddressRequest createAddressRequest
) {
}
