package com.danionescu.checker;

import com.danionescu.model.UrlProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Map;

public class CheckUrlTask implements Runnable {
    private String USER_AGENT = "website-checker";
    private UrlProperties urlProperties;
    private Map<String, Boolean> urlStatuses = null;

    public CheckUrlTask(Map<String, Boolean> urlStatuses, UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.urlStatuses = urlStatuses;
    }

    public void run() {
        HttpClient client = getHttpClient();
        HttpGet request = new HttpGet(this.urlProperties.getLink());
        request.addHeader("User-Agent", this.USER_AGENT);
        try {
            client.execute(request);
        } catch (IOException e) {
            urlStatuses.put(this.urlProperties.getLink(), false);
            return;
        }

        urlStatuses.put(this.urlProperties.getLink(), true);
    }

    private HttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(this.urlProperties.getTimeout())
                .setConnectionRequestTimeout(this.urlProperties.getTimeout())
                .setSocketTimeout(this.urlProperties.getTimeout()).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
}