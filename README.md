# Website Categorization Service

This Java project categorizes web pages based on predefined keyword categories. It uses **Spring Boot** to build a REST API and **Jsoup** for web scraping. The project allows you to input a list of URLs, fetch their content, and classify them into categories based on matching keywords.

## Features

- Fetches and processes web page content using URLs.
- Categorizes web pages based on keyword matching.
- Handles HTTP redirects and large web pages.
- Provides an API endpoint to input URLs and retrieve their categories.
- Includes proper input validation and error handling (e.g., timeouts, invalid URLs).

## Technologies Used

- **Java 8**
- **Spring Boot** for building the REST API
- **Jsoup** for HTML parsing and web scraping
- **Gradle** for building and dependency management
- **JUnit** and **Mockito** for testing

## Prerequisites

- **Java 8** must be installed. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html).
- **Gradle** must be installed. You can download it from [here](https://gradle.org/install/).

## Project Structure

```plaintext
├── src
│   ├── main
│   │   └── java
│   │       └── org
│   │           └── example
│   │               ├── controllers
│   │               │   └── UrlCategoryController.java   # REST controller to handle incoming requests
│   │               ├── models
│   │               │   └── Category.java                # Model for keyword categories
│   │               ├── services
│   │               │   ├── UrlClassificationService.java # Service to classify URLs based on categories
│   │               └── UrlTextExtractorService.java      # Service to fetch and parse URL content
├── build.gradle                                         # Gradle build file
└── README.md                                            # Project documentation
