package com.esempla.libraryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LibraryclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryclientApplication.class, args);
	}
}
