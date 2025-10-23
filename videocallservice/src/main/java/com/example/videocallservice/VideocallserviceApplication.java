package com.example.videocallservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VideocallserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideocallserviceApplication.class, args);
	}

}
