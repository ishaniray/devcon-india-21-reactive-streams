package com.cerner.devcon.udi.controller;

import java.time.Duration;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.devcon.hic.models.Rating;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MockUdiReactiveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockUdiReactiveController.class);

	@PostMapping("/consume")
	public Mono<String> consumePayload(@RequestBody Flux<Rating> ratings) {
		return ratings.doOnNext(rating -> LOGGER.info(rating.toString()))
				.then(Mono.just("Received flux at: " + ZonedDateTime.now().toString()))
				.delayElement(Duration.ofSeconds(5));
	}
}
