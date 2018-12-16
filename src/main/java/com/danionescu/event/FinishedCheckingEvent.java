package com.danionescu.event;

import com.danionescu.model.CheckerParameters;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class FinishedCheckingEvent extends ApplicationEvent {
    private HashMap<String, Boolean> urlStatuses;
    private CheckerParameters checkerParameters;

    public FinishedCheckingEvent(Object source, HashMap<String, Boolean> urlStatuses, CheckerParameters checkerParameters) {
        super(source);
        this.urlStatuses = urlStatuses;
        this.checkerParameters = checkerParameters;
    }

    public HashMap<String, Boolean> getUrlStatuses() {
        return urlStatuses;
    }

    public CheckerParameters getCheckerParameters() {
        return checkerParameters;
    }
}