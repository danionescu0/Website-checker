package com.danionescu.rest.client;

import com.danionescu.main.RegexListChecker;
import com.danionescu.model.UrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.*;

@Service
public class AsyncWebsiteStatusClientImpl implements AsyncWebsiteStatusClient {
    private AsyncHttpClient asyncHttpClient;
    private RegexListChecker regexListChecker;

    @Autowired
    public AsyncWebsiteStatusClientImpl(AsyncHttpClient asyncHttpClient, RegexListChecker regexListChecker) {
        this.asyncHttpClient = asyncHttpClient;
        this.regexListChecker = regexListChecker;
    }

    @Override
    public CompletableFuture<Boolean> check(UrlProperties urlProperties) {
        return this.asyncHttpClient
                .prepareGet(urlProperties.getUri().toString())
                .setRequestTimeout(urlProperties.getTimeout())
                .execute()
                .toCompletableFuture()
                .thenApply(resp -> {
                      if (resp.getStatusCode() != 200) {
                          return false;
                      }
                      return this.regexListChecker.isValid(resp.getResponseBody(), urlProperties.getRegexMatchers());
                })
                .exceptionally(t -> false);
    }
}