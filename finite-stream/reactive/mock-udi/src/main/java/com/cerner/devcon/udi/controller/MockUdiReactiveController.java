package com.cerner.devcon.udi.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class MockUdiReactiveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockUdiReactiveController.class);

	@PostMapping("/consume")
	public Mono<String> consumePayload(@RequestBody Mono<String> param) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return param.doOnNext(LOGGER::info).then(Mono.just("Received at: " + new Date()));
	}

}
