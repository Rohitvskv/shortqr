package com.kdei.shortqr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UrlRequest(

        @NotBlank(message = "URL cannot be empty")
        @Pattern(
                regexp = "^https?://[^\\s]+$",
                message = "URL must be a valid HTTP or HTTPS URL"
        )
        String url,

        @Size(max = 20, message = "Short code cannot exceed 20 characters")
        @Pattern(
                regexp = "^[A-Za-z0-9_-]*$",
                message = "Alias may contain only letters, numbers, _ and -"
        )
        String customAlias
) {
}