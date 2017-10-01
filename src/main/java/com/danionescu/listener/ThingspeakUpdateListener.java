package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.rest.client.ThingsPeak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
final public class ThingspeakUpdateListener {
    private ThingsPeak thingsPeak;

    @Autowired
    public ThingspeakUpdateListener(ThingsPeak thingsPeak) {
        this.thingsPeak = thingsPeak;
    }

    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        HashMap<String, Boolean> urlStatuses = finishedCheckingEvent.getUrlStatuses();
        if (urlStatuses.isEmpty()) {
            return;
        }
        urlStatuses.forEach((website, status) -> {
            System.out.println(String.format("Site: %s is %s", website, status ? "up" : "down"));
            this.thingsPeak.markWebsiteDown(website);
        });
    }
}