package com.danionescu.util;

import org.springframework.stereotype.Service;

@Service
public class CmdExecImpl implements CmdExec {
    @Override
    public void executeCommand(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            String[] commands = new String[]{
                    "/bin/sh",
                    "-c",
                    command
            };
            runtime.exec(commands);
        } catch (Exception e) {
            System.out.println(String.format("Command %s failed with reason: %s", command, e.getMessage()));
        }
    }
}