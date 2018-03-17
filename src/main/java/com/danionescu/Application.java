package com.danionescu;

import com.beust.jcommander.JCommander;
import com.danionescu.application.CliParams;
import com.danionescu.main.WebsiteStatus;
import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.model.UrlProperties;
import com.danionescu.main.UrlPropertiesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private WebsiteStatus websiteStatus;

    @Autowired
    private UrlPropertiesProvider urlPropertiesProvider;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String[] args) throws IOException {
        CliParams cliParams = new CliParams();
        new JCommander(cliParams, args);
        ArrayList<UrlProperties> urlList = this.urlPropertiesProvider.get(cliParams.getFile());

        HashMap<String, Boolean> urlStatuses = this.websiteStatus.getUrlStatuses(urlList);
        this.eventPublisher.publishEvent(new FinishedCheckingEvent(this, urlStatuses, cliParams));
        System.exit(SpringApplication.exit(context));
    }
}