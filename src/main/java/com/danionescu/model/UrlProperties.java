package com.danionescu.model;

import java.net.URI;

public class UrlProperties {
    private URI uri;
    private int timeout;

    public UrlProperties(URI uri, int timeout) {
        this.uri = uri;
        this.timeout = timeout;
    }

    public URI getUri() {
        return this.uri;
    }

    public int getTimeout() {
        return timeout;
    }
}