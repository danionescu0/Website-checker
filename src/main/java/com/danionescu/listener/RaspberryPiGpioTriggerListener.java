package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.gpio.GpioDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public final class RaspberryPiGpioTriggerListener extends GpioTriggerListener {

    @Autowired
    @Qualifier("raspberry-gpio")
    private GpioDriver gpio;

    @Autowired
    public RaspberryPiGpioTriggerListener(ThreadPoolTaskExecutor taskExecutor,
                                          @Value("${gpio.pinHoldTime}") int pinHoldTime) {

        super(taskExecutor, pinHoldTime);
    }

    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        String pinName = finishedCheckingEvent.getCliParams().getGpioPi();
        if (null == pinName) {
            return;
        }
        this.taskExecutor.execute(new RunGpioInBackground(pinName, this.pinHoldTime, this.gpio));
    }
}