package com.danionescu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaspberryPiGpio implements GpioDriver {
    private CmdExec cmdExec;
    private String setOutputCommand = "gpio mode %s out";
    private String writePinCommand = "gpio write %s %s";

    @Autowired
    public RaspberryPiGpio(CmdExec cmdExec) {
        this.cmdExec = cmdExec;
    }

    @Override
    public void set(String pin, boolean state) {
        setOutput(pin);
        String command = String.format(this.writePinCommand, pin, state ? "1" : "0");
        this.cmdExec.executeCommand(command);
    }

    private void setOutput(String pin) {
        String command = String.format(this.setOutputCommand, pin);
        this.cmdExec.executeCommand(command);
    }
}