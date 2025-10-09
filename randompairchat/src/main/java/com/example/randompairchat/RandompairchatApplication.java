package com.example.randompairchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RandompairchatApplication {
	public static void main(String[] args) {
		SpringApplication.run(RandompairchatApplication.class, args);
	}
}
