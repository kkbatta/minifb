package com.interview.pocketfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class PocketFbApplication {

	public static void main(String[] args) {
		UsersManager.INSTANCE.init();
		SpringApplication.run(PocketFbApplication.class, args);
	}
	
	
	
}
