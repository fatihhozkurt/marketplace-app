package com.fatih.marketplace_app.dto.request.user;

import com.fatih.marketplace_app.annotation.OptionalFieldValidation;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserRequest(

        @NotNull
        UUID userId,

        @OptionalFieldValidation(notBlank = true, min = 2, max = 50)
        String firstName,

        @OptionalFieldValidation(notBlank = true, min = 2, max = 50)
        String lastName,

        @OptionalFieldValidation(notBlank = true, min = 13, max = 345, pattern = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        String email,

        @OptionalFieldValidation(
                notBlank = true,
                min = 11,
                max = 13,
                pattern = "^(\\+90|0)?5\\d{9}$"
        )
        String phone,

        @OptionalFieldValidation(
                notBlank = true,
                min = 8,
                max = 16,
                pattern = "^(?=.*[A-ZÇĞİÖŞÜ])(?=.*[a-zçğıöşü])(?=.*[0-9]).*$"
        )
        String password

) {
}
