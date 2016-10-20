package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ThingspeakUpdateListener {
    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        System.out.println("url list::");
        ConcurrentHashMap<String, Boolean> urlStatuses = finishedCheckingEvent.getUrlStatuses();
        if (urlStatuses.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Boolean> urlStatus : urlStatuses.entrySet()) {
            System.out.println(String.format("Status for url %s is:%s", urlStatus.getKey(), urlStatus.getValue()));
        }
    }
}
