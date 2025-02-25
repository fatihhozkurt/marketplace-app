package com.fatih.marketplace_app.exception;

/**
 * Exception thrown when a business rule is violated.
 */
public class BusinessException extends RuntimeException {

    /**
     * Constructs a new {@link BusinessException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public BusinessException(String message) {
        super(message);
    }
}
