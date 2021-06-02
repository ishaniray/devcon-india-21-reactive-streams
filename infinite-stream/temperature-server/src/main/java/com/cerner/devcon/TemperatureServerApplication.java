package com.cerner.devcon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TemperatureServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureServerApplication.class, args);
	}

}
