package com.fatih.marketplace_app.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        Integer errorCode,
        String message,
        LocalDateTime dateTime
) {
}
