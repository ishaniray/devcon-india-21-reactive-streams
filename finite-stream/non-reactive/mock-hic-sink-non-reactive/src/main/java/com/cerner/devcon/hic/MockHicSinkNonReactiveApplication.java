package com.cerner.devcon.hic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MockHicSinkNonReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockHicSinkNonReactiveApplication.class, args);
	}
}
