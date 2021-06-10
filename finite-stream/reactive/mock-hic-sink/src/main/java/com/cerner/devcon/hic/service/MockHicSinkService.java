package com.cerner.devcon.hic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.cerner.devcon.hic.constants.CrawlInd;
import com.cerner.devcon.hic.dao.RatingRepository;
import com.cerner.devcon.hic.models.Rating;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@Service
public class MockHicSinkService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockHicSinkService.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RatingRepository ratingRepository;

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	@RabbitListener(queues = "${application.rabbit.queue}", concurrency = "3")
	public void sendFlux(String id) throws JsonProcessingException {

		LOGGER.info("Item received from queue: " + id);

		Flux<Rating> test = ratingRepository.findByCrawlInd(CrawlInd.NOT_STARTED.getIndicator());

		test.doOnNext(r -> LOGGER.info(r.toString())).subscribe();

		/*
		 * Mono<String> jsonPayload =
		 * Mono.just(objectMapper.writeValueAsString(fetchInfoDAO.fetchInfo(id)));
		 * 
		 * Mono<String> monoResponse =
		 * WebClient.create("http://localhost:7071").method(HttpMethod.POST).uri(
		 * "/consume") .body(jsonPayload,
		 * String.class).retrieve().bodyToMono(String.class);
		 * 
		 * monoResponse.doOnNext(LOGGER::info).subscribe();
		 */
	}
}
