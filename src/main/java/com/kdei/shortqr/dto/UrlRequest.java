package com.kdei.shortqr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UrlRequest(

        @NotBlank(message = "URL cannot be empty")
        @Pattern(
                regexp = "^(https?://).+",
                message = "URL must start with http:// or https://"
        )
        String url,

        @Size(max = 20, message = "Short code cannot exceed 20 characters")
        String customAlias

) {
}