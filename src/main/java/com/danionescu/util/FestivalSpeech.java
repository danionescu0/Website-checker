package com.danionescu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Uses linux "festival" texy to speech software
 * how to use it: https://wiki.archlinux.org/index.php/Festival
 */
@Service
public class FestivalSpeech implements Speech {
    private CmdExec cmdExec;

    @Autowired
    public FestivalSpeech(CmdExec cmdExec) {
        this.cmdExec = cmdExec;
    }

    @Override
    public void say(String text) {
        String[] command = {
                "/bin/sh",
                "-c",
                String.format("echo \"%s\" | festival --tts", text)
        };
        this.cmdExec.executeCommands(command);
    }
}