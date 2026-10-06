package com.kdei.shortqr.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ShortUrlNotFoundException ex) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(ShortUrlExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleExpired(
            ShortUrlExpiredException ex) {

        return buildResponse(
                HttpStatus.GONE,
                "EXPIRED",
                ex.getMessage()
        );
    }

    @ExceptionHandler(DuplicateShortCodeException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(
            DuplicateShortCodeException ex) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "DUPLICATE_SHORT_CODE",
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request");

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                message
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(
            Exception ex) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred"
        );
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String error,
            String message) {

        Map<String, Object> body = new HashMap<>();

        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);

        return ResponseEntity
                .status(status)
                .body(body);
    }
}