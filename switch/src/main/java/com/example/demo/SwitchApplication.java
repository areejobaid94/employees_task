package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
		"com.example.something", "com.example.demo"})
public class SwitchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SwitchApplication.class, args);
	}
}
