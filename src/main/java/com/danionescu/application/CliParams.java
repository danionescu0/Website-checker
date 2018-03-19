package com.danionescu.application;

import com.beust.jcommander.Parameter;

public class CliParams {
    @Parameter(names = {"--file", "-f"}, required = true,
            description = "The path to the file containing the url list for scanning")
    public String file;

    @Parameter(names = {"--verbal-alerts", "-va"}, required = false,
            description = "Enable speach for verbal site down alerts")
    public boolean verbalAlerts;

    @Parameter(names = {"--gpio-pi", "gp"}, required = false,
            description = "Enable triggering a raspberry pi pin")
    public String gpioPi;

    @Parameter(names = {"--gpio-chip", "gc"}, required = false,
            description = "Enable triggering a C.H.i.P. pin")
    public String gpioChip;

    @Parameter(names = {"--email-alert", "ea"}, required = false,
            description = "Sends email alert")
    public boolean emailAlert;

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