package com.kdei.shortqr.dto;

import java.time.LocalDateTime;

public record UrlResponse(
        String originalUrl,
        String shortCode,
        String shortUrl,
        String qrCodeUrl,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        Long clickCount
) {
}