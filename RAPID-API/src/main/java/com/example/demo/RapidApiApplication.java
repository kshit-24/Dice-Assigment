package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class } , scanBasePackages = "com.example.exception")
@Configuration
@ComponentScan
public class RapidApiApplication {

	public static void main(String[] args) {
		System.out.println("Application is running");
		SpringApplication.run(RapidApiApplication.class, args);
	}

}
