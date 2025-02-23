package com.fatih.marketplace_app.dto.request.address;

import com.fatih.marketplace_app.annotation.OptionalFieldValidation;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAddressRequest(

        @NotNull
        UUID addressId,

        @OptionalFieldValidation(notBlank = true, min = 3, max = 50)
        String country,

        @OptionalFieldValidation(notBlank = true, min = 3, max = 50)
        String city,

        @OptionalFieldValidation(notBlank = true, min = 3, max = 50)
        String district,

        @OptionalFieldValidation(notBlank = true, min = 3, max = 60)
        String neighbourhood,

        @OptionalFieldValidation(notBlank = true, min = 5, max = 60)
        String street,

        @OptionalFieldValidation(notBlank = true, min = 5, max = 60)
        String apartment,

        @OptionalFieldValidation(notBlank = true, min = 1, max = 6)
        String apartmentNumber,

        @OptionalFieldValidation(notBlank = true, min = 1, max = 5)
        String zipCode
) {
}
