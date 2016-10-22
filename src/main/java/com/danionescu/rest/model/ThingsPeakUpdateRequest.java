package com.danionescu.rest.model;

import java.util.HashMap;

public class ThingsPeakUpdateRequest {
    private String apiKey;
    private String field1;
    private HashMap<String, String> attributes;

    public ThingsPeakUpdateRequest(String apiKey, String field1) {
        this.apiKey = apiKey;
        this.field1 = field1;
        this.attributes = new HashMap<>();
        this.attributes.put("api_key", apiKey);
        this.attributes.put("field1", field1);
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getField1() {
        return field1;
    }
}