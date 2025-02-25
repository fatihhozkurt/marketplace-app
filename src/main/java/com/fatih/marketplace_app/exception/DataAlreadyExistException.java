package com.fatih.marketplace_app.exception;

/**
 * Exception thrown when an attempt is made to create or insert data that already exists.
 */
public class DataAlreadyExistException extends RuntimeException {

    /**
     * Constructs a new {@link DataAlreadyExistException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public DataAlreadyExistException(String message) {
        super(message);
    }
}
