package com.danionescu.main;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegexListCheckerImpl implements RegexListChecker {
    @Override
    public boolean isValid(String body, List<String> expressions) {
        for (String expression: expressions) {
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(body);
            if (!matcher.find()) {
                return false;
            }
        }

        return true;
    }
}