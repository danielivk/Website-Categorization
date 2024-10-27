package org.example.models;

import java.util.List;

public class Category {
    private String name;
    private List<String> keywords;

    public Category(String name, List<String> keywords) {
        this.name = name;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}

