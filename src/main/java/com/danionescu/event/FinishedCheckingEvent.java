package com.danionescu.event;

import org.springframework.context.ApplicationEvent;

import java.util.concurrent.ConcurrentHashMap;

public class FinishedCheckingEvent extends ApplicationEvent {
    private ConcurrentHashMap<String, Boolean> urlStatuses;

    public FinishedCheckingEvent(Object source, ConcurrentHashMap<String, Boolean> urlStatuses) {
        super(source);
        this.urlStatuses = urlStatuses;
    }

    public ConcurrentHashMap<String, Boolean> getUrlStatuses() {
        return urlStatuses;
    }

    public void setUrlStatuses(ConcurrentHashMap<String, Boolean> urlStatuses) {
        this.urlStatuses = urlStatuses;
    }
}
