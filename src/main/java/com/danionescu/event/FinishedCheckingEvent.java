package com.danionescu.event;

import com.danionescu.application.CliParams;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class FinishedCheckingEvent extends ApplicationEvent {
    private HashMap<String, Boolean> urlStatuses;
    private CliParams cliParams;

    public FinishedCheckingEvent(Object source, HashMap<String, Boolean> urlStatuses, CliParams cliParams) {
        super(source);
        this.urlStatuses = urlStatuses;
        this.cliParams = cliParams;
    }

    public HashMap<String, Boolean> getUrlStatuses() {
        return urlStatuses;
    }

    public CliParams getCliParams() {
        return cliParams;
    }
}