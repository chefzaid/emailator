package com.emailator.bulksender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmailBulkSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailBulkSenderApplication.class, args);
	}
}
