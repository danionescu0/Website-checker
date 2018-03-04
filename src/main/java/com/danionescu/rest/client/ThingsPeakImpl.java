package com.danionescu.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ThingsPeakImpl implements ThingsPeak {
    private RestTemplate restTemplate;
    private String updateUrl = "https://api.thingspeak.com/update.json";
    private String apiKey;

    @Autowired
    public ThingsPeakImpl(@Value("${thingspeak.api.key}") String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    @Override
    public void markWebsiteDown(String siteName) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        request.add("api_key", this.apiKey);
        request.add("field1", siteName);
        this.restTemplate.postForObject(this.updateUrl, request, String.class);
    }
}