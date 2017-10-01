package com.danionescu.rest.client;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public interface AsyncWebsiteStatusClient {
    CompletableFuture<Boolean> check(URI uri);
}