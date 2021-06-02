package com.cerner.devcon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.devcon.dto.TempLevel;
import com.cerner.devcon.service.TempLevelService;

import reactor.core.publisher.Flux;

@RestController
public class TempController {

	private TempLevelService tempLevelService;

	@Autowired
	public TempController(TempLevelService tempLevelService) {
		this.tempLevelService = tempLevelService;
	}

	@GetMapping(value = "/ssn/{ssn}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<TempLevel> findBySsn(@PathVariable("ssn") long ssn) {
		return tempLevelService.findBySsn(ssn);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<TempLevel> findAll() {
		return tempLevelService.findAll();
	}
}
