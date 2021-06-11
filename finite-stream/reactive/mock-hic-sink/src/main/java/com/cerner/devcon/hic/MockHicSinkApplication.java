package com.cerner.devcon.hic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableScheduling
public class MockHicSinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockHicSinkApplication.class, args);
	}
}
