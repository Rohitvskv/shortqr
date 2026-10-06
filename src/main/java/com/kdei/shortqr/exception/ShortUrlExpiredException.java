package com.kdei.shortqr.exception;

public class ShortUrlExpiredException extends RuntimeException {

    public ShortUrlExpiredException(String message) {
        super(message);
    }
}