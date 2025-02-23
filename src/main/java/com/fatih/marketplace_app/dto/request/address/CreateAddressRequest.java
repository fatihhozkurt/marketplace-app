package com.fatih.marketplace_app.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAddressRequest(

        @NotNull
        @NotBlank
        @Size(min = 3, max = 50)
        String country,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 50)
        String city,


        @NotNull
        @NotBlank
        @Size(min = 3, max = 50)
        String district,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 60)
        String neighbourhood,


        @NotNull
        @NotBlank
        @Size(min = 5, max = 60)
        String street,


        @NotNull
        @NotBlank
        @Size(min = 5, max = 60)
        String apartment,


        @NotNull
        @NotBlank
        @Size(min = 1, max = 6)
        String apartmentNumber,


        @NotNull
        @NotBlank
        @Size(min = 5, max = 5)
        String zipCode
) {
}
