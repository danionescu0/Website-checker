package com.danionescu.rest.client;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.*;

@Service
public class AsyncWebsiteStatusClientImpl implements AsyncWebsiteStatusClient {
    private AsyncHttpClient asyncHttpClient;

    public AsyncWebsiteStatusClientImpl(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    @Override
    public CompletableFuture<Boolean> check(URI uri) {
        return this.asyncHttpClient
                .prepareGet(uri.toString())
                .execute()
                .toCompletableFuture()
                .thenApply(resp -> resp.getStatusCode() != 200 ? false : true)
                .exceptionally(t -> false);
    }
}