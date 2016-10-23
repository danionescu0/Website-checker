package com.danionescu.util;

import org.springframework.stereotype.Service;

@Service
public class CmdExec {
    public void executeCommand(String command) {
        Runtime rt = Runtime.getRuntime();
        try {
            String[] commands = new String[]{
                    "/bin/sh",
                    "-c",
                    command
            };
            Process pr = rt.exec(commands);
            pr.getInputStream();
        } catch (Exception e) {
            System.out.println(String.format("Command %s failed with reason: %s", command, e.getMessage()));
        }
    }
}