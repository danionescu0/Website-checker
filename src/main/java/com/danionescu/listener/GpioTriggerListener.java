package com.danionescu.listener;

import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.util.GpioDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class GpioTriggerListener {
    private GpioDriver gpio;
    private String pin;
    private int pinHoldTime;
    private ThreadPoolTaskExecutor taskExecutor;

    private class RunGpioInBackground implements Runnable {
        private String pin;
        private int pinHoldTime;
        private GpioDriver gpio;

        public RunGpioInBackground(String pin, int pinHoldTime, GpioDriver gpio) {
            this.pin = pin;
            this.pinHoldTime = pinHoldTime;
            this.gpio = gpio;
        }

        @Override
        public void run() {
            this.gpio.set(this.pin, true);
            try {
                Thread.sleep(this.pinHoldTime);
            } catch (InterruptedException e) {
                System.out.println("Unable to sleep during set gpio pin");
            }
            this.gpio.set(this.pin, false);
        }
    }

    @Autowired
    public GpioTriggerListener(GpioDriver gpio, ThreadPoolTaskExecutor taskExecutor,
                               @Value("${gpio.raspberrypi.pin}") String pin,
                               @Value("${gpio.pinHoldTime}") int pinHoldTime) {
        this.gpio = gpio;
        this.taskExecutor = taskExecutor;
        this.pin = pin;
        this.pinHoldTime = pinHoldTime;
    }

    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {
        if (!finishedCheckingEvent.getCliParams().hasGpioTrigger()) {
            return;
        }
        this.taskExecutor.execute(new RunGpioInBackground(this.pin, this.pinHoldTime, this.gpio));
    }
}