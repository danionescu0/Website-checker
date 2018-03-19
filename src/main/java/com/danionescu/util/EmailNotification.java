package com.danionescu.util;

public interface EmailNotification {
    void notify(String to, String subject, String body);
}