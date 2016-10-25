package com.danionescu.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebsiteStatus {

    private ThreadPoolTaskExecutor taskExecutor;
    private ConcurrentHashMap<String, Boolean> urlStatuses;

    @Autowired
    public WebsiteStatus(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public ConcurrentHashMap<String, Boolean> getUrlStatuses(ArrayList<String> urlList) {
        urlStatuses = new ConcurrentHashMap<>();
        for (String url: urlList) {
            this.taskExecutor.execute(new CheckUrlTask(urlStatuses, url));
        }
        while (taskExecutor.getActiveCount() > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return urlStatuses;
    }
}