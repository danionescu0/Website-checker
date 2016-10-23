package com.danionescu.rest.client;

import com.danionescu.rest.template.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ThingsPeak {
    private Builder restTemplateBuilder;
    private String updateUrl = "https://api.thingspeak.com/update.json";
    private String apiKey;

    @Autowired
    public ThingsPeak(@Value("${thingspeak.api.key}") String apiKey, Builder restTemplateBuilder) {
        this.apiKey = apiKey;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public void markWebsiteDown(String siteName) {
        RestTemplate restTemplate = this.restTemplateBuilder.buildDefaultRestTemplate();
        MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        request.add("api_key", this.apiKey);
        request.add("field1", siteName);
        restTemplate.postForObject(this.updateUrl, request, String.class);
    }
}