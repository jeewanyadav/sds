package com.batteries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.batteries")
public class BatteriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatteriesApplication.class, args);
	}

}
