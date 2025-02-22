package com.fatih.marketplace_app.dto.request.product;

import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotNull
        @NotBlank
        @Size(min = 5, max = 50)
        String productName,

        @NotNull
        @NotBlank
        @Size(min = 50, max = 500)
        String productDescription,

        @NotNull(message = "Product price cannot be null")
        @DecimalMin(value = "1.00", message = "Product price must be at least 1.00 TL")
        @Digits(integer = 6, fraction = 2, message = "Product price must have at most 6 digits before the decimal and 2 digits after")
        BigDecimal productPrice,

        @NotNull
        @PositiveOrZero
        Long stockQuantity
) {
}
