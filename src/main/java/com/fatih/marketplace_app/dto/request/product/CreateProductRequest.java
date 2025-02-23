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
        @DecimalMin(value = "1.00")
        @Digits(integer = 12, fraction = 2)
        BigDecimal productPrice,

        @NotNull
        @Positive
        Long stockQuantity
) {
}
