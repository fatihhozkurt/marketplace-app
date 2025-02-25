package com.fatih.marketplace_app.exception;

import java.time.LocalDateTime;

/**
 * Represents an error response returned to the client when an exception occurs.
 *
 * @param errorCode the numerical error code associated with the error
 * @param message   the detailed error message
 * @param dateTime  the timestamp when the error occurred
 */
public record ErrorResponse(
        Integer errorCode,
        String message,
        LocalDateTime dateTime
) {
}
