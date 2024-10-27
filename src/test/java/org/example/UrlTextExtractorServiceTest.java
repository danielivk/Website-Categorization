package org.example;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UrlTextExtractorServiceTest {

    private UrlTextExtractorService urlTextExtractorService;

    @Before
    public void setUp() {
        urlTextExtractorService = new UrlTextExtractorService();
    }

    @Test
    public void testFetchTextFromValidUrl() throws Exception {
        String url = "https://edition.cnn.com/sport";

        // This test will actually fetch the content from CNN
        String content = urlTextExtractorService.fetchTextFromUrl(url);

        assertNotNull(content);
        assertTrue(content.length() > 0);
    }

    @Test(expected = IOException.class)
    public void testFetchTextFromInvalidUrl() throws Exception {
        String url = "http://invalid.url";

        // This should throw an IOException because the URL is invalid
        urlTextExtractorService.fetchTextFromUrl(url);
    }
}

