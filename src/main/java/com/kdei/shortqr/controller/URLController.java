package com.kdei.shortqr.controller;

import com.kdei.shortqr.dto.UrlRequest;
import com.kdei.shortqr.dto.UrlResponse;
import com.kdei.shortqr.entity.URL;
import com.kdei.shortqr.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class URLController {

    private final UrlService urlService;

    public URLController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(
            @Valid @RequestBody UrlRequest request) {

        UrlResponse response = urlService.createShortUrl(request);

        return ResponseEntity.ok(response);
    }
}