package com.danionescu.util;

import org.springframework.stereotype.Service;

@Service
public class FestivalSpeech implements Speech {
    @Override
    public void say(String text) {
        String[] command = {
                "/bin/sh",
                "-c",
                String.format("echo \"%s\" | festival --tts", text)
        };
        executeCommand(command);
    }

    private void executeCommand(String[] command) {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(command);
            pr.getInputStream();
        } catch (Exception e) {
            System.out.println("Speech failed with reason:" + e.getMessage());
        }
    }
}