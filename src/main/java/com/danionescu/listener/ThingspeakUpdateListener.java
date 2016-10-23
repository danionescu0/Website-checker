package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.rest.client.ThingsPeak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ThingspeakUpdateListener {
    private ThingsPeak thingsPeak;

    @Autowired
    public ThingspeakUpdateListener(ThingsPeak thingsPeak) {
        this.thingsPeak = thingsPeak;
    }

    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        ConcurrentHashMap<String, Boolean> urlStatuses = finishedCheckingEvent.getUrlStatuses();
        if (urlStatuses.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Boolean> urlStatus : urlStatuses.entrySet()) {
            System.out.println(String.format("Status for url %s is:%s", urlStatus.getKey(), urlStatus.getValue()));
        }
        urlStatuses.forEach((website, status) -> {
            this.thingsPeak.markWebsiteDown(website);
        });
    }
}