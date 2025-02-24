package com.fatih.marketplace_app.dto.request.invoice;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateInvoiceRequest(
        @NotNull
        UUID orderId
) {
}
