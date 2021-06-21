package com.cerner.devcon.udi.controller;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.devcon.hic.models.Rating;
import com.cerner.devcon.subscriber.RatingSubscriber;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MockUdiReactiveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockUdiReactiveController.class);

	private RatingSubscriber<Rating> ratingSubscriber;

	@Autowired
	public MockUdiReactiveController(RatingSubscriber<Rating> ratingSubscriber) {
		this.ratingSubscriber = ratingSubscriber;
	}

	@PostMapping("/consume")
	public Mono<String> consumePayload(@RequestBody Flux<Rating> ratings) throws InterruptedException {
		Thread.sleep(1000);
		return ratings.subscribe(ratingSubscriber); //.then(Mono.just("Received flux at: " + ZonedDateTime.now().toString()));
	}
}
