package com.cerner.devcon.udi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockUdiNonReactiveController {

	@PostMapping(path = "/consume")
	@ResponseStatus(code = HttpStatus.OK)
	public void consumePayload(@RequestBody String param) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
