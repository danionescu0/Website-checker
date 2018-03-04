package com.danionescu.rest.client;

import com.danionescu.model.UrlProperties;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.*;

@Service
public class AsyncWebsiteStatusClientImpl implements AsyncWebsiteStatusClient {
    private AsyncHttpClient asyncHttpClient;

    public AsyncWebsiteStatusClientImpl(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    @Override
    public CompletableFuture<Boolean> check(UrlProperties urlProperties) {
        return this.asyncHttpClient
                .prepareGet(urlProperties.getUri().toString())
                .setRequestTimeout(urlProperties.getTimeout())
                .execute()
                .toCompletableFuture()
                .thenApply(resp -> resp.getStatusCode() == 200)
                .exceptionally(t -> false);
    }
}