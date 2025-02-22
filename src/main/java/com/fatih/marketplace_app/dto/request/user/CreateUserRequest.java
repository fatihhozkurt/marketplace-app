package com.fatih.marketplace_app.dto.request.user;

import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @NotNull
        @NotBlank
        @Size(min = 2, max = 50)
        String firstName,

        @NotNull
        @NotBlank
        @Size(min = 2, max = 50)
        String lastName,

        @NotNull
        @NotBlank
        @Email
        @Size(min = 13, max = 345)
        String email,

        @NotNull
        @NotEmpty(message = "Telefon numarası boş bırakılamaz.")
        @Pattern(regexp = "^(\\+90|0)?5\\d{9}$",
                message = "Geçersiz telefon numarası. Lütfen Türkiye formatında bir numara giriniz (örneğin, +905321234567 veya 05321234567).")
        String phone,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-ZÇĞİÖŞÜ])(?=.*[a-zçğıöşü])(?=.*[0-9]).*$")
        @Size(min = 8, max = 16)
        String password
) {
}
