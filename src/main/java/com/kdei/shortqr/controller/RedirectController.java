package com.kdei.shortqr.controller;

import com.kdei.shortqr.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public void redirect(
            @PathVariable String shortCode,
            HttpServletResponse response) throws IOException {

        String originalUrl =
                urlService.getOriginalUrl(shortCode);

        response.sendRedirect(originalUrl);
    }
}