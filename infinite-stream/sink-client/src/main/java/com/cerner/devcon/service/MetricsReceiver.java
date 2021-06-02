package com.cerner.devcon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cerner.devcon.dto.OxyLevel;
import com.cerner.devcon.dto.TempLevel;
import com.cerner.devcon.subscriber.OxySubscriber;
import com.cerner.devcon.subscriber.TempSubscriber;

import reactor.core.publisher.Flux;

@Service
public class MetricsReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetricsReceiver.class);

	private OxySubscriber<OxyLevel> oxySubscriber;

	private TempSubscriber<TempLevel> tempSubscriber;

	@Autowired
	public MetricsReceiver(OxySubscriber<OxyLevel> oxySubscriber, TempSubscriber<TempLevel> tempSubscriber) {
		this.oxySubscriber = oxySubscriber;
		this.tempSubscriber = tempSubscriber;
	}

	public void getMetrics() {
		getOxyLevels();
		getTempLevels();
	}

	public void doSomething() {
		LOGGER.info("Doing something else...");
	}

	private void getOxyLevels() {

		WebClient client = WebClient.create("http://localhost:9095");

		ParameterizedTypeReference<OxyLevel> type = new ParameterizedTypeReference<OxyLevel>() {
		};

		Flux<OxyLevel> oxyLevelStream = client.get().uri("/").retrieve().bodyToFlux(type);

		oxyLevelStream.subscribe(oxySubscriber);
	}

	private void getTempLevels() {

		WebClient client = WebClient.create("http://localhost:9096");

		ParameterizedTypeReference<TempLevel> type = new ParameterizedTypeReference<TempLevel>() {
		};

		Flux<TempLevel> tempLevelStream = client.get().uri("/").retrieve().bodyToFlux(type);

		tempLevelStream.subscribe(tempSubscriber);
	}
}
