package com.cerner.devcon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.devcon.dto.OxyLevel;
import com.cerner.devcon.service.OxyLevelService;

import reactor.core.publisher.Flux;

@RestController
public class OxyController {

	private OxyLevelService oxyLevelService;

	@Autowired
	public OxyController(OxyLevelService oxyLevelService) {
		this.oxyLevelService = oxyLevelService;
	}

	@GetMapping(value = "/ssn/{ssn}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<OxyLevel> findBySsn(@PathVariable("ssn") long ssn) {
		return oxyLevelService.findBySsn(ssn);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<OxyLevel> findAll() {
		return oxyLevelService.findAll();
	}
}
