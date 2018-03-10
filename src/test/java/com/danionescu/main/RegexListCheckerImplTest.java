package com.danionescu.main;

import com.danionescu.main.RegexListCheckerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RegexListCheckerImplTest {
    @Test
    public void isValid() {
        String textToValidate = "Some html body:\n" +
                "some header 1234 \n" +
                "some body 677 \n" +
                "some footer";

        RegexListCheckerImpl regexListChecker = new RegexListCheckerImpl();
        List<String> matchingExpressions = Arrays.asList(".*html.*", "header [1-9]*");
        Assert.assertTrue(regexListChecker.isValid(textToValidate, matchingExpressions));

        List<String> matchingLastWordExpression = Arrays.asList(".*footer.*");
        Assert.assertTrue(regexListChecker.isValid(textToValidate, matchingLastWordExpression));

        List<String> nonMatchingExpression = Arrays.asList(".*some.*", ".*other.*");
        Assert.assertFalse(regexListChecker.isValid(textToValidate, nonMatchingExpression));
    }
}