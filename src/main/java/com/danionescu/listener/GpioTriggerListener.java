package com.danionescu.listener;

import com.danionescu.gpio.GpioDriver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

abstract public class GpioTriggerListener {
    protected int pinHoldTime;
    protected ThreadPoolTaskExecutor taskExecutor;

    protected class RunGpioInBackground implements Runnable {
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
                Thread.sleep(this.pinHoldTime * 1000);
            } catch (InterruptedException e) {
                System.out.println("Unable to sleep during set gpio pin");
            }
            this.gpio.set(this.pin, false);
        }
    }

    public GpioTriggerListener(ThreadPoolTaskExecutor taskExecutor, int pinHoldTime) {
        this.taskExecutor = taskExecutor;
        this.pinHoldTime = pinHoldTime;
    }
}