package com.danionescu.util;

import org.springframework.stereotype.Service;

@Service
public class CmdExec {
    public void executeCommands(String[] commands) {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(commands);
            pr.getInputStream();
        } catch (Exception e) {
            System.out.println(String.format("Command %s failed with reason: %s", commands, e.getMessage()));
        }
    }

    public void executeCommand(String command) {
        executeCommands(new String[]{
                "/bin/sh",
                "-c",
                command
        });
    }
}