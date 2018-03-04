package com.danionescu.model;

import java.net.URI;
import java.util.List;

public class UrlProperties {
    private URI uri;
    private int timeout;
    private List<String> regexMatchers;

    public UrlProperties(URI uri, int timeout, List<String> regexMatchers) {
        this.uri = uri;
        this.timeout = timeout;
        this.regexMatchers = regexMatchers;
    }

    public URI getUri() {
        return this.uri;
    }

    public int getTimeout() {
        return timeout;
    }

    public List<String> getRegexMatchers() {
        return regexMatchers;
    }
}