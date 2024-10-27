package org.example;

import org.example.models.Category;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Runner {

    // Method to initialize categories and keywords
    public static List<Category> initializeModel() {
        List<Category> categories = new ArrayList<>();

        // Category 1: Star Wars
        categories.add(new Category("Star Wars", Arrays.asList(
                "star war", "starwars", "r2d2", "may the force be with you"
        )));

        // Category 2: Basketball
        categories.add(new Category("Basketball", Arrays.asList(
                "basketball", "nba", "ncaa", "lebron james", "john stokton", "anthony davis"
        )));

        return categories;
    }

    public static void main(String[] args) {
        List<Category> categories = initializeModel();
        List<String> urls = Arrays.asList(
                "http://www.starwars.com",
                "https://www.imdb.com/find/?q=star%20wars&ref_=nv_sr_sm",
                "https://edition.cnn.com/sport"
        );

        classifyUrls(categories, urls);
    }

    // Method to classify URLs based on keyword matches in the text
    public static void classifyUrls(List<Category> categories, List<String> urls) {
        UrlTextExtractorService extractorService = new UrlTextExtractorService();

        for (String url : urls) {
            try {
                // Fetch the text content from the URL
                String text = extractorService.fetchTextFromUrl(url).toLowerCase(); // Case insensitive

                List<String> matchingCategories = new ArrayList<>();

                // Check each category's keywords against the text
                for (Category category : categories) {
                    for (String keyword : category.getKeywords()) {
                        // Use a regex pattern to ensure case-insensitive matching
                        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(keyword.toLowerCase()) + "\\b");

                        if (pattern.matcher(text).find()) {
                            matchingCategories.add(category.getName());
                            break;  // Once one keyword matches, stop checking further keywords for this category
                        }
                    }
                }

                // Print the results
                if (!matchingCategories.isEmpty()) {
                    System.out.println("URL: " + url + " matches categories: " + matchingCategories);
                } else {
                    System.out.println("URL: " + url + " matches no categories.");
                }

            } catch (Exception e) {
                System.err.println("Failed to process URL: " + url);
            }
        }
    }
}
