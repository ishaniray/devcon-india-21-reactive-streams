package com.cerner.devcon.udi.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.devcon.hic.models.Rating;

@RestController
public class MockUdiNonReactiveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockUdiNonReactiveController.class);

	@PostMapping(path = "/consume")
	@ResponseStatus(code = HttpStatus.OK)
	public String consumePayload(@RequestBody List<Rating> ratings) {
		ratings.stream().map(Rating::toString).forEach(LOGGER::info);
		return "Received at: " + ZonedDateTime.now().toString();
	}
}
