package com.danionescu.application;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("application.properties")
public class Config {

    @Bean(name = "org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor springMailSender() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(25);

        return threadPoolTaskExecutor;
    }

    @Bean
    AsyncHttpClient asyncHttpClient() {
        return new DefaultAsyncHttpClient();
    }

    @Bean
    public RestTemplate buildDefaultRestTemplate() {
        return new RestTemplate();
    }
}