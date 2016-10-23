package com.danionescu;

import com.beust.jcommander.JCommander;
import com.danionescu.application.CliParams;
import com.danionescu.checker.WebsiteStatus;
import com.danionescu.event.FinishedCheckingEvent;
import com.danionescu.rest.client.ThingsPeak;
import com.danionescu.util.UrlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    public WebsiteStatus websiteStatus;

    @Autowired
    public ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public UrlProvider urlProvider;

    @Autowired
    public ApplicationEventPublisher eventPublisher;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String[] args) throws IOException {
        CliParams cliParams = new CliParams();
        new JCommander(cliParams, args);
        ArrayList<String> urlList = this.urlProvider.get(cliParams.getFile());

        ConcurrentHashMap<String, Boolean> urlStatuses = this.websiteStatus.getUrlStatuses(urlList);
        this.eventPublisher.publishEvent(new FinishedCheckingEvent(this, urlStatuses, cliParams));
        this.taskExecutor.shutdown();
    }
}