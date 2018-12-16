package com.danionescu.listener;

import com.danionescu.model.CheckerParameters;
import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.util.Speech;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;


public class VerbalAlertListenerTest {
    @Test
    public void noVerbalAlersConfigured() {
        Speech speech = Mockito.mock(Speech.class);
        Mockito.doNothing().when(speech).say(Mockito.anyString());
        VerbalAlertListener verbalAlertListener = new VerbalAlertListener(speech);
        verbalAlertListener.onFinishedChecking(getFinishedCheckingEvent(false));
        Mockito.verify(speech, Mockito.times(0)).say(Mockito.anyString());
    }

    @Test
    public void speakVerbalAlerts() {
        Speech speech = Mockito.mock(Speech.class);
        Mockito.doNothing().when(speech).say("Multiple websites are down");
        Mockito.doNothing().when(speech).say("Website test.com is down");

        VerbalAlertListener verbalAlertListener = new VerbalAlertListener(speech);
        FinishedCheckingEvent finishedCheckingEvent = getFinishedCheckingEvent(true);

        HashMap<String, Boolean> urlStatuses = new HashMap<>();
        urlStatuses.put("http://test1.com", true);
        Mockito.when(finishedCheckingEvent.getUrlStatuses()).thenReturn(urlStatuses);
        verbalAlertListener.onFinishedChecking(finishedCheckingEvent);
        Mockito.verify(speech, Mockito.times(0)).say(Mockito.anyString());

        urlStatuses.clear();
        urlStatuses.put("http://test1.com", false);
        urlStatuses.put("http://test2.com", false);
        urlStatuses.put("http://test3.com", true);
        Mockito.when(finishedCheckingEvent.getUrlStatuses()).thenReturn(urlStatuses);
        verbalAlertListener.onFinishedChecking(finishedCheckingEvent);
        Mockito.verify(speech, Mockito.times(1)).say("Multiple websites are down");
        urlStatuses.clear();
        urlStatuses.put("http://test1.com", true);
        urlStatuses.put("http://test.com", false);
        Mockito.when(finishedCheckingEvent.getUrlStatuses()).thenReturn(urlStatuses);
        verbalAlertListener.onFinishedChecking(finishedCheckingEvent);
        Mockito.verify(speech, Mockito.times(1)).say("Website test.com is down");
    }

    private FinishedCheckingEvent getFinishedCheckingEvent(boolean hasVerbalAlerts) {
        CheckerParameters checkerParameters = Mockito.mock(CheckerParameters.class);
        Mockito.when(checkerParameters.hasVerbalAlerts()).thenReturn(hasVerbalAlerts);
        FinishedCheckingEvent finishedCheckingEvent = Mockito.mock(FinishedCheckingEvent.class);
        Mockito.when(finishedCheckingEvent.getCheckerParameters()).thenReturn(checkerParameters);


        return finishedCheckingEvent;
    }


}