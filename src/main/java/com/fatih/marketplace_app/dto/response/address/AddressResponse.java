package com.fatih.marketplace_app.dto.response.address;

import java.util.UUID;

public record AddressResponse(

        UUID addressId,

        String country,

        String city,

        String district,

        String neighbourhood,

        String street,

        String apartment,

        String apartmentNumber,

        String zipCode
) {
}