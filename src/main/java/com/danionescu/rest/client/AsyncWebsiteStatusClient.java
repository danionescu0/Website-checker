package com.danionescu.rest.client;

import com.danionescu.model.UrlProperties;

import java.util.concurrent.CompletableFuture;

public interface AsyncWebsiteStatusClient {
    CompletableFuture<Boolean> check(UrlProperties urlProperties);
}