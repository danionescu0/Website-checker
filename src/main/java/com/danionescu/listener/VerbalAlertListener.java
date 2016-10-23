package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.util.Speech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class VerbalAlertListener {
    private Speech speech;
    private String oneFailedText = "Website %s is down";
    private String multipleFailedText = "Multiple websites are down";

    @Autowired
    public VerbalAlertListener(Speech speech) {
        this.speech = speech;
    }

    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        if (!finishedCheckingEvent.getCliParams().hasVerbalAlerts()) {
            return;
        }
        this.speech.say(getTextToSpeech(finishedCheckingEvent.getUrlStatuses()));
    }


    private String getTextToSpeech(ConcurrentHashMap<String, Boolean> urlStatuses) {
        if (getNrFailedUrls(urlStatuses) > 1) {
            return this.multipleFailedText;
        }

        return String.format(this.oneFailedText, getFailedUrlWithoutSchema(urlStatuses));
    }

    private long getNrFailedUrls(ConcurrentHashMap<String, Boolean> urlStatuses) {
        return urlStatuses.entrySet().stream()
                        .filter(map -> !map.getValue())
                        .map(map->map.getKey())
                        .count();
    }

    private String getFailedUrlWithoutSchema(ConcurrentHashMap<String, Boolean> urlStatuses) {
        String url = urlStatuses.entrySet().stream()
                        .filter(map -> !map.getValue())
                        .map(map->map.getKey())
                        .collect(Collectors.joining());

       return url.replaceFirst("^(http://www\\.|http://|www\\.)","");
    }
}