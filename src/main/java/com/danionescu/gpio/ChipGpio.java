package com.danionescu.gpio;

import com.danionescu.util.CmdExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("chip-gpio")
public class ChipGpio implements GpioDriver {
    private CmdExec cmdExec;
    private String changePinStateCommand = "python -c 'import CHIP_IO.GPIO as GPIO;GPIO.setup(\"%s\", GPIO.OUT);" +
            "GPIO.output(\"%s\", GPIO.%s)'";

    @Autowired
    public ChipGpio(CmdExec cmdExec) {
        this.cmdExec = cmdExec;
    }

    @Override
    public void set(String pin, boolean state) {
        String command = String.format(this.changePinStateCommand, pin, pin, state ? "HIGH" : "LOW");
        this.cmdExec.executeCommand(command);
    }
}
