package com.danionescu.checker;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Map;

public class CheckUrlTask implements Runnable {
    private String USER_AGENT = "website-checker";
    private String url;
    private Map<String, Boolean> urlStatuses = null;

    public CheckUrlTask(Map<String, Boolean> urlStatuses, String url) {
        this.url = url;
        this.urlStatuses = urlStatuses;
    }

    public void run() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpGet request = new HttpGet(this.url);
        request.addHeader("User-Agent", this.USER_AGENT);
        try {
            client.execute(request);
        } catch (IOException e) {
            urlStatuses.put(this.url, false);
            return;
        }

        urlStatuses.put(this.url, true);
    }
}