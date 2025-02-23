package com.fatih.marketplace_app.dto.request.product;

import com.fatih.marketplace_app.annotation.OptionalFieldValidation;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductRequest(

        @NotNull
        UUID id,

        @OptionalFieldValidation(notBlank = true, min = 5, max = 50)
        String productName,

        @OptionalFieldValidation(notBlank = true, min = 50, max = 500)
        String productDescription,

        @DecimalMin(value = "0.01")
        @Digits(integer = 12, fraction = 2)
        BigDecimal productPrice,

        @PositiveOrZero
        Long stockQuantity
) {
}
