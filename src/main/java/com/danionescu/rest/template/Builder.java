package com.danionescu.rest.template;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Builder {

    public RestTemplate buildDefaultRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }
}