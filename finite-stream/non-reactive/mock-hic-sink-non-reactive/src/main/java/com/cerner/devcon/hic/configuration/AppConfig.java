package com.cerner.devcon.hic.configuration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Bean
	public AtomicInteger getAtomicInteger() {
		return new AtomicInteger(10000);
	}

	@Bean
	public Random getRandom() {
		return new Random();
	}

	@Bean
	public Queue queue() {
		return new Queue("processor", true);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
