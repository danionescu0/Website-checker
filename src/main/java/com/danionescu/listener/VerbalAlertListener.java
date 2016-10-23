package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.util.Speech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VerbalAlertListener {
    private Speech speech;

    @Autowired
    public VerbalAlertListener(Speech speech) {
        this.speech = speech;
    }

    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        this.speech.say("anne has apples");
    }
}