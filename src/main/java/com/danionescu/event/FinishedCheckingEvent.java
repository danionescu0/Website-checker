package com.danionescu.event;

import com.danionescu.application.CliParams;
import org.springframework.context.ApplicationEvent;

import java.util.concurrent.ConcurrentHashMap;

public class FinishedCheckingEvent extends ApplicationEvent {
    private ConcurrentHashMap<String, Boolean> urlStatuses;
    private CliParams cliParams;

    public FinishedCheckingEvent(Object source, ConcurrentHashMap<String, Boolean> urlStatuses, CliParams cliParams) {
        super(source);
        this.urlStatuses = urlStatuses;
        this.cliParams = cliParams;
    }

    public ConcurrentHashMap<String, Boolean> getUrlStatuses() {
        return urlStatuses;
    }

    public void setUrlStatuses(ConcurrentHashMap<String, Boolean> urlStatuses) {
        this.urlStatuses = urlStatuses;
    }

    public CliParams getCliParams() {
        return cliParams;
    }
}