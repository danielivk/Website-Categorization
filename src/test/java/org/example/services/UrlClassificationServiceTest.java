package org.example.services;

import org.example.UrlTextExtractorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UrlClassificationServiceTest {

    private UrlClassificationService urlClassificationService;
    private UrlTextExtractorService urlTextExtractorService;

    @Before
    public void setUp() {
        urlTextExtractorService = Mockito.mock(UrlTextExtractorService.class);
        urlClassificationService = new UrlClassificationService(urlTextExtractorService);
    }

    @Test
    public void testCategorizeUrls_withStarWarsContent() throws Exception {
        String url = "http://www.starwars.com";
        String starWarsContent = "In a galaxy far, far away... Star Wars is the best!";

        // Mocking the URL text extractor service to return the star wars content
        when(urlTextExtractorService.fetchTextFromUrl(url)).thenReturn(starWarsContent);

        List<String> urls = Collections.singletonList(url);
        Map<String, List<String>> result = urlClassificationService.classifyUrls(urls);

        assertTrue(result.get(url).contains("Star Wars"));
    }

    @Test
    public void testCategorizeUrls_withImdbStarWarsContent() throws Exception {
        String url = "https://www.imdb.com/find?q=star+wars&ref_=nv_sr_sm";
        String imdbStarWarsContent = "Find results for Star Wars movies and actors.";

        // Mocking the URL text extractor service to return the IMDb star wars content
        when(urlTextExtractorService.fetchTextFromUrl(url)).thenReturn(imdbStarWarsContent);

        List<String> urls = Collections.singletonList(url);
        Map<String, List<String>> result = urlClassificationService.classifyUrls(urls);

        assertTrue(result.get(url).contains("Star Wars"));
    }

    @Test
    public void testCategorizeUrls_withCnnSportContent() throws Exception {
        String url = "https://edition.cnn.com/sport";
        String cnnSportContent = "Latest news from basketball, NBA, and NCAA.";

        // Mocking the URL text extractor service to return the CNN sport content
        when(urlTextExtractorService.fetchTextFromUrl(url)).thenReturn(cnnSportContent);

        List<String> urls = Collections.singletonList(url);
        Map<String, List<String>> result = urlClassificationService.classifyUrls(urls);

        assertTrue(result.get(url).contains("Basketball"));
    }
}

