package com.kdei.shortqr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "urls",
        indexes = {
                @Index(name = "idx_short_code", columnList = "short_code", unique = true)
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(name = "short_code", nullable = false, unique = true, length = 20)
    private String shortCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "click_count", nullable = false)
    private Long clickCount = 0L;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (clickCount == null) {
            clickCount = 0L;
        }
    }
}