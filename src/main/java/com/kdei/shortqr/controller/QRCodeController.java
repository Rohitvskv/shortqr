package com.kdei.shortqr.controller;

import com.kdei.shortqr.service.QRCodeService;
import com.kdei.shortqr.service.UrlService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    private final QRCodeService qrCodeService;
    private final UrlService urlService;

    public QRCodeController(
            QRCodeService qrCodeService,
            UrlService urlService) {

        this.qrCodeService = qrCodeService;
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<byte[]> generateQRCode(
            @PathVariable String shortCode) {

        if (!urlService.exists(shortCode)) {
            return ResponseEntity.notFound().build();
        }

        String shortUrl =
                "http://localhost:8080/" + shortCode;

        try {

            byte[] qrCode =
                    qrCodeService.generateQRCode(
                            shortUrl,
                            300,
                            300
                    );

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCode);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}