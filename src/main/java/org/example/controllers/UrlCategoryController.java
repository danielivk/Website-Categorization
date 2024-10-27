package org.example.controllers;

import org.example.services.UrlClassificationService;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UrlCategoryController {

    private static final int MAX_URL_LENGTH = 2000;  // Maximum allowed URL length

    private final UrlClassificationService urlClassificationService;

    public UrlCategoryController(UrlClassificationService urlClassificationService) {
        this.urlClassificationService = urlClassificationService;
    }

    @PostMapping("/categorize")
    public Map<String, List<String>> categorizeUrls(@RequestBody List<String> urls) {
        // Validate all the URLs before passing them to the service
        List<String> validUrls = urls.stream()
                .filter(this::isValidUrl)   // Validate URL format and length
                .collect(Collectors.toList());

        // Call the service only with valid URLs
        return urlClassificationService.classifyUrls(validUrls);
    }

    // Helper method to check if the URL is valid and not too long
    private boolean isValidUrl(String url) {
        if (url.length() > MAX_URL_LENGTH) {
            System.err.println("URL too long: " + url);  // Log too long URL
            return false;  // Reject URLs that are too long
        }

        try {
            new URL(url);  // Validate the URL format
            return true;
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + url);  // Log invalid URL
            return false;
        }
    }
}
