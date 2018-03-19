package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.util.EmailNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailAlertListener  {
    private EmailNotification emailNotification;
    private String mailNotifiedAddress;

    @Autowired
    public EmailAlertListener(EmailNotification emailNotification,
                              @Value("${mail.notifiedAddress}") String mailNotifiedAddress) {
        this.emailNotification = emailNotification;
        this.mailNotifiedAddress = mailNotifiedAddress;
    }

    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        if (!finishedCheckingEvent.getCliParams().hasEmailAlert()) {
            return;
        }
        if (getNrFailedUrls(finishedCheckingEvent.getUrlStatuses()) == 0) {
            return;
        }
        String title = "Some websites in the list are down";
        List<String> websitesDown =
                finishedCheckingEvent.getUrlStatuses().entrySet().stream()
                .filter(map -> !map.getValue())
                .map(map->map.getKey())
                .collect(Collectors.toList());
        String body = websitesDown.stream().map(Object::toString).collect(Collectors.joining(", "));
        this.emailNotification.notify(this.mailNotifiedAddress, title, body);
    }

    //@Todo fix duplicate code with number urls from verbal alert listener
    private long getNrFailedUrls(HashMap<String, Boolean> urlStatuses) {
        return urlStatuses.entrySet().stream()
                .filter(map -> !map.getValue())
                .map(map->map.getKey())
                .count();
    }
}