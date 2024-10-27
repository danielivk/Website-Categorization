package org.example.services;

import org.example.UrlTextExtractorService;
import org.example.models.Category;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UrlClassificationService {

    private final UrlTextExtractorService extractorService;
    private final List<Category> categories;

    public UrlClassificationService(UrlTextExtractorService extractorService) {
        this.extractorService = extractorService;
        this.categories = initializeModel();
    }

    // Initialize categories and keywords
    private List<Category> initializeModel() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Star Wars", Arrays.asList("star war", "starwars", "r2d2", "may the force be with you")));
        categories.add(new Category("Basketball", Arrays.asList("basketball", "nba", "ncaa", "lebron james", "john stockton", "anthony davis")));
        return categories;
    }

    // Method to classify URLs based on categories and keywords
    public Map<String, List<String>> classifyUrls(List<String> urls) {
        Map<String, List<String>> result = new HashMap<>();

        for (String url : urls) {
            try {
                // Fetch the text content from the URL
                String text = extractorService.fetchTextFromUrl(url).toLowerCase(); // Case insensitive
                List<String> matchingCategories = new ArrayList<>();

                // Check each category's keywords against the text
                for (Category category : categories) {
                    for (String keyword : category.getKeywords()) {
                        if (text.contains(keyword.toLowerCase())) {
                            matchingCategories.add(category.getName());
                            break;  // Stop checking further keywords for this category once matched
                        }
                    }
                }

                result.put(url, matchingCategories.isEmpty() ? Collections.singletonList("No matching categories") : matchingCategories);

            } catch (java.net.SocketTimeoutException e) {
                result.put(url, Collections.singletonList("Timeout while fetching the URL"));
            } catch (Exception e) {
                result.put(url, Collections.singletonList("Error fetching or processing URL"));
            }
        }

        return result;
    }
}
