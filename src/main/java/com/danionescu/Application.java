package com.danionescu;

import com.danionescu.checker.WebsiteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    public WebsiteStatus websiteStatus;

	public static void main(String[] args) {
        System.out.print("asdasdas");
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String[] args) throws IOException {
        for (String arg:args){
            System.out.print(arg);

        }
        ArrayList<String> urlList= new ArrayList<>();
        urlList.add("http://www.sentimente.ro");
        urlList.add("http://www.bohus.ro");
        urlList.add("http://www.cicibici.ro");
        urlList.add("http://www.wolframalpha.com/");

        ArrayList<String> badUrlList = websiteStatus.getUnresponsiveUrls(urlList);
        System.out.println("Bad url list:");
        for (String url: badUrlList) {
            System.out.println(url);
        }
    }
}