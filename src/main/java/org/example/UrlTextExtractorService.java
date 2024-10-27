package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class UrlTextExtractorService {

    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 10000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private static final int MAX_REDIRECTS = 5;          // Maximum number of redirects allowed

    public String fetchTextFromUrl(String urlString) throws Exception {
        return fetchTextFromUrlWithRedirects(urlString, 0);  // Start with zero redirects
    }

    // Helper method to handle redirects
    private String fetchTextFromUrlWithRedirects(String urlString, int redirectCount) throws Exception {
        if (redirectCount > MAX_REDIRECTS) {
            throw new IOException("Too many redirects");
        }

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Referer", "https://www.google.com");  // Optional, to mimic a real browser
        connection.setInstanceFollowRedirects(false);  // We handle redirects manually

        int responseCode = connection.getResponseCode();

        // Handle 3xx redirect responses
        if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == 308) {
            String newUrl = connection.getHeaderField("Location");

            if (newUrl == null) {
                throw new IOException("Received redirect status but no Location header was found");
            }

            // Check if the new URL is relative or absolute
            if (!newUrl.startsWith("http")) {
                // Build a full URL from the base URL and the relative redirect URL
                URL baseUrl = new URL(urlString);  // Original URL
                newUrl = new URL(baseUrl, newUrl).toString();  // Resolve relative URL against the base URL
            }

            // Follow the redirect
            return fetchTextFromUrlWithRedirects(newUrl, redirectCount + 1);
        } else if (responseCode == HttpURLConnection.HTTP_OK) {
            // Fetch the raw HTML content
            StringBuilder content = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
            }

            // Use Jsoup to parse the HTML and extract the text
            Document doc = Jsoup.parse(content.toString());
            doc.select("script, style, img, iframe").remove();  // Remove scripts, styles, and media (images, iframes)
            return doc.text();  // Extract and return the visible text
        } else {
            throw new IOException("Failed to fetch content. HTTP response code: " + responseCode);
        }
    }
}
