package com.fatih.marketplace_app.dto.response.user;

import java.util.UUID;

public record UserResponse(

        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
