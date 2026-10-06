package com.kdei.shortqr.service;

import com.kdei.shortqr.dto.UrlRequest;
import com.kdei.shortqr.dto.UrlResponse;
import com.kdei.shortqr.entity.URL;
import com.kdei.shortqr.exception.DuplicateShortCodeException;
import com.kdei.shortqr.exception.ShortUrlExpiredException;
import com.kdei.shortqr.exception.ShortUrlNotFoundException;
import com.kdei.shortqr.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int SHORT_CODE_LENGTH = 6;

    private final SecureRandom random = new SecureRandom();

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public UrlResponse createShortUrl(UrlRequest request) {

        String shortCode;

        if (request.customAlias() != null &&
                !request.customAlias().isBlank()) {

            shortCode = request.customAlias().trim();

            if (urlRepository.existsByShortCode(shortCode)) {
                throw new DuplicateShortCodeException(
                        "Short code already exists"
                );
            }

        } else {
            shortCode = generateUniqueShortCode();
        }

        URL url = URL.builder()
                .originalUrl(request.url())
                .shortCode(shortCode)
                .build();

        URL savedUrl = urlRepository.save(url);

        return mapToResponse(savedUrl);
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {

        URL url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(
                                "Short URL not found"
                        ));

        if (url.getExpiresAt() != null &&
                url.getExpiresAt().isBefore(LocalDateTime.now())) {

            throw new ShortUrlExpiredException(
                    "Short URL has expired"
            );
        }

        urlRepository.incrementClickCount(shortCode);

        return url.getOriginalUrl();
    }

    public boolean exists(String shortCode) {
        return urlRepository.existsByShortCode(shortCode);
    }

    private String generateUniqueShortCode() {

        String shortCode;

        do {
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    private String generateShortCode() {

        StringBuilder code =
                new StringBuilder(SHORT_CODE_LENGTH);

        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {

            int index =
                    random.nextInt(CHARACTERS.length());

            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }

    private UrlResponse mapToResponse(URL url) {

        String shortUrl =
                baseUrl + "/" + url.getShortCode();

        String qrCodeUrl =
                baseUrl + "/api/qr/" + url.getShortCode();

        return new UrlResponse(
                url.getOriginalUrl(),
                url.getShortCode(),
                shortUrl,
                qrCodeUrl,
                url.getCreatedAt(),
                url.getExpiresAt(),
                url.getClickCount()
        );
    }

    public String getShortUrl(String shortCode) {

        if (!urlRepository.existsByShortCode(shortCode)) {
            throw new ShortUrlNotFoundException(
                    "Short URL not found"
            );
        }

        return baseUrl + "/" + shortCode;
    }
}