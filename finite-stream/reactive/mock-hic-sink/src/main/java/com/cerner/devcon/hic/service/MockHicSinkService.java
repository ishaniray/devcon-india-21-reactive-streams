package com.cerner.devcon.hic.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cerner.devcon.hic.dao.FetchInfoDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class MockHicSinkService {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private FetchInfoDAO fetchInfoDAO;

	@RabbitListener(queues = "${spring.cloud.stream.bindings.input.destination}", concurrency = "5")
	public void sendFlux(String id) throws JsonProcessingException {
		System.out.println("Item received from queue: " + id);

		Mono<String> jsonPayload = Mono.just(objectMapper.writeValueAsString(fetchInfoDAO.fetchInfo(id)));

		Mono<String> monoResponse = WebClient.create("http://localhost:7071").method(HttpMethod.POST).uri("/consume")
				.body(jsonPayload, String.class).retrieve().bodyToMono(String.class);

		monoResponse.doOnNext(System.out::println).subscribe(); // use block() for the blocking
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

}
