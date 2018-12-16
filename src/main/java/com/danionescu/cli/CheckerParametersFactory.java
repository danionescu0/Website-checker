package com.danionescu.cli;

import com.beust.jcommander.JCommander;
import com.danionescu.model.CheckerParameters;
import org.springframework.stereotype.Service;

@Service
public class CheckerParametersFactory {
    public CheckerParameters create(String[] args) {
        JcomanderCliParams params = new JcomanderCliParams();
        JCommander.newBuilder()
                .addObject(params)
                .build()
                .parse(args);

        return new CheckerParameters(params.file, params.verbalAlerts,
                params.gpioPi, params.gpioChip, params.emailAlert);
    }
}