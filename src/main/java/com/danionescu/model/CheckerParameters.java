package com.danionescu.model;

public class CheckerParameters {
    public String file;
    public boolean verbalAlerts;
    public String gpioPi;
    public String gpioChip;
    public boolean emailAlert;

    public CheckerParameters(String file, boolean verbalAlerts, String gpioPi, String gpioChip, boolean emailAlert) {
        this.file = file;
        this.verbalAlerts = verbalAlerts;
        this.gpioPi = gpioPi;
        this.gpioChip = gpioChip;
        this.emailAlert = emailAlert;
    }

    public String getFile() {
        return this.file;
    }

    public boolean hasVerbalAlerts() {
        return this.verbalAlerts;
    }

    public String getGpioPi() {
        return gpioPi;
    }

    public String getGpioChip() {
        return gpioChip;
    }

    public boolean hasEmailAlert() {
        return this.emailAlert;
    }
}