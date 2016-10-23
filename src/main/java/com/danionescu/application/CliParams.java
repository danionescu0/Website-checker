package com.danionescu.application;

import com.beust.jcommander.Parameter;

public class CliParams {
    @Parameter(names = {"--file", "-f"}, required = true,
            description = "The path to the file containing the url list for scanning")
    public String file;

    @Parameter(names = {"--verbal-alerts", "-va"}, required = false,
            description = "Enable speach for verbal site down alerts")
    public boolean verbalAlerts;

    @Parameter(names = {"--gpio-triger", "-gt"}, required = false,
            description = "Enable triggering a gpio pin")
    public boolean gpioTrigger;

    public String getFile() {
        return this.file;
    }

    public boolean hasVerbalAlerts() {
        return this.verbalAlerts;
    }

    public boolean hasGpioTrigger() {
        return gpioTrigger;
    }
}