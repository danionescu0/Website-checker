package com.danionescu.main;

import java.util.List;

public interface RegexListChecker {
    boolean isValid(String body, List<String> expressions);
}