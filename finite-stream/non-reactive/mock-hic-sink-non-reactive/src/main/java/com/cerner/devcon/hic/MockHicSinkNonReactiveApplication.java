package com.cerner.devcon.hic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.messaging.Sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cerner.devcon.hic.dao.FetchInfoDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableBinding(Sink.class)
@SpringBootApplication
public class MockHicSinkNonReactiveApplication {

	private static Logger logger = LoggerFactory.getLogger(MockHicSinkNonReactiveApplication.class);

	private static final String REST_ENDPOINT = "http://localhost:8081/consume";

	@Autowired
	private FetchInfoDAO fetchInfoDAO;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@StreamListener(Sink.INPUT)
	public void loggerSink(String id) throws JsonProcessingException {
		logger.info("Received from processor queue: " + id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> payload = new HttpEntity<>(objectMapper.writeValueAsString(fetchInfoDAO.fetchInfo(id)),
				headers);
		ResponseEntity<Void> response = restTemplate.exchange(REST_ENDPOINT, HttpMethod.POST, payload, Void.class);

		logger.info("Response received from udi: " + response.getStatusCodeValue());

	}

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(MockHicSinkNonReactiveApplication.class, args);
	}

}
