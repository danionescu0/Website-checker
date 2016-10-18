package com.danionescu.checker;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebsiteStatus {

    private class MessagePrinterTask implements Runnable {
        private String url;
        private Map<String, Boolean> urlStatuses = null;

        public MessagePrinterTask(Map<String, Boolean> urlStatuses, String url) {
            this.url = url;
            this.urlStatuses = urlStatuses;
        }

        public void run() {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(this.url);
            request.addHeader("User-Agent", "website-checker");
            try {
                client.execute(request);
            } catch (IOException e) {
                urlStatuses.put(this.url, false);
                return;
            }
            urlStatuses.put(this.url, true);
        }
    }

    private ThreadPoolTaskExecutor taskExecutor;
    protected ConcurrentHashMap<String, Boolean> urlStatuses;


    @Autowired
    public WebsiteStatus(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public ArrayList<String> getUnresponsiveUrls(ArrayList<String> urlList) {
        urlStatuses = new ConcurrentHashMap<>();
        for (String url: urlList) {
            taskExecutor.execute(new MessagePrinterTask(urlStatuses, url));
        }
        while (taskExecutor.getActiveCount() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return doGetUnresponsiveUrls();
    }

    private ArrayList<String> doGetUnresponsiveUrls() {
        ArrayList<String> urlList = new ArrayList<>();
        if (urlStatuses.isEmpty()) {
            return urlList;
        }
        for (Map.Entry<String, Boolean> urlStatus : urlStatuses.entrySet()) {
            if (false == urlStatus.getValue()) {
                urlList.add(urlStatus.getKey());
            }
        }

        return urlList;
    }
}