package com.fatih.marketplace_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler to manage application-wide exceptions
 * and return structured error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ResourceNotFoundException} and returns a 404 Not Found response.
     *
     * @param ex the thrown {@link ResourceNotFoundException}
     * @return a {@link ResponseEntity} containing the error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link DataAlreadyExistException} and returns a 409 Conflict response.
     *
     * @param ex the thrown {@link DataAlreadyExistException}
     * @return a {@link ResponseEntity} containing the error details
     */
    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> dataAlreadyExist(DataAlreadyExistException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link BusinessException} and returns a 400 Bad Request response.
     *
     * @param ex the thrown {@link BusinessException}
     * @return a {@link ResponseEntity} containing the error details
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> dataAlreadyExist(BusinessException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}