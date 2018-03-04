package com.danionescu.main;

import com.danionescu.model.UrlProperties;
import com.danionescu.rest.client.AsyncWebsiteStatusClient;
import com.danionescu.rest.client.AsyncWebsiteStatusClientImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class WebsiteStatus {
    private AsyncWebsiteStatusClient asyncWebsiteStatusClient;

    public WebsiteStatus(AsyncWebsiteStatusClientImpl asyncWebsiteStatusClient) {
        this.asyncWebsiteStatusClient = asyncWebsiteStatusClient;
    }

    public HashMap<String, Boolean> getUrlStatuses(List<UrlProperties> urlList) {
        ArrayList<CompletableFuture<Boolean>> futures = new ArrayList<>();
        HashMap<String, Boolean> statuses = new HashMap<>();
        ArrayList<String> uris = new ArrayList<>();
        for (UrlProperties urlProperties: urlList) {
            futures.add(this.asyncWebsiteStatusClient.check(urlProperties));
            uris.add(urlProperties.getUri().toString());
        }
        CompletableFuture<Void> combinedFutures =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));

        int position = 0;
        combinedFutures.join();
        for (CompletableFuture<Boolean> future : futures) {
            try {
                statuses.put(uris.get(position), future.get());
            }
            catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e.getMessage());
            }
            position ++;
        }


        return statuses;
    }
}