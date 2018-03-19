package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.util.Speech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.stream.Collectors;

@Component
public final class VerbalAlertListener {
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
        if (getNrFailedUrls(finishedCheckingEvent.getUrlStatuses()) == 0) {
            return;
        }
        this.speech.say(getTextToSpeech(finishedCheckingEvent.getUrlStatuses()));
    }


    private String getTextToSpeech(HashMap<String, Boolean> urlStatuses) {
        if (getNrFailedUrls(urlStatuses) > 1) {
            return this.multipleFailedText;
        }

        return String.format(this.oneFailedText, getFailedUrlWithoutSchema(urlStatuses));
    }

    private long getNrFailedUrls(HashMap<String, Boolean> urlStatuses) {
        return urlStatuses.entrySet().stream()
                        .filter(map -> !map.getValue())
                        .map(map->map.getKey())
                        .count();
    }

    private String getFailedUrlWithoutSchema(HashMap<String, Boolean> urlStatuses) {
        String url = urlStatuses.entrySet().stream()
                        .filter(map -> !map.getValue())
                        .map(map->map.getKey())
                        .collect(Collectors.joining());

       return url.replaceFirst("^(http://www\\.|http://|www\\.)","");
    }
}