package com.kdei.shortqr.exception;

public class DuplicateShortCodeException extends RuntimeException {

    public DuplicateShortCodeException(String message) {
        super(message);
    }
}