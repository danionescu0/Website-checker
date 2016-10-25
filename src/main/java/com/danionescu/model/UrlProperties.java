package com.danionescu.model;

public class UrlProperties {
    private String link;
    private int timeout;

    public UrlProperties(String link, int timeout) {
        this.link = link;
        this.timeout = timeout;
    }

    public String getLink() {
        return link;
    }

    public int getTimeout() {
        return timeout;
    }
}