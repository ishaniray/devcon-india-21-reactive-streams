package com.cerner.devcon.hic.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cerner.devcon.hic.dao.RatingDao;
import com.cerner.devcon.hic.models.Rating;

@Service
public class MockHicSinkService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockHicSinkService.class);

	private static final String REST_ENDPOINT = "http://localhost:8081/consume";

	private AtomicInteger atomicInteger;

	private Random random;

	private RatingDao ratingDao;

	private Queue queue;

	private RabbitTemplate rabbitTemplate;

	private RestTemplate restTemplate;

	@Autowired
	public MockHicSinkService(AtomicInteger atomicInteger, Random random, RatingDao ratingDao, Queue queue,
			RabbitTemplate rabbitTemplate, RestTemplate restTemplate) {
		this.atomicInteger = atomicInteger;
		this.random = random;
		this.ratingDao = ratingDao;
		this.queue = queue;
		this.rabbitTemplate = rabbitTemplate;
		this.restTemplate = restTemplate;
	}

	@Scheduled(fixedRate = 10000)
	public void insertRowsIntoDbAndPublishMsg() {

		int counter = 0;
		Map<String, Integer> batchMinMax = new HashMap<>();

		for (int i = 0; i < 25; ++i) {

			int orderId = atomicInteger.getAndIncrement();
			int customerId = 100 + random.nextInt(900);
			int restaurantId = 1000 + random.nextInt(1000);
			int deliveryAgentId = 2000 + random.nextInt(1000);
			int foodRating = 1 + random.nextInt(5);
			int deliveryRating = 1 + random.nextInt(5);

			Rating rating = new Rating(null, orderId, customerId, restaurantId, deliveryAgentId, foodRating,
					deliveryRating);

			ratingDao.insertRow(rating);

			if (counter == 0) {
				batchMinMax.put("min", orderId);
			}

			if (++counter == 5) {
				counter = 0;
				batchMinMax.put("max", orderId);
				rabbitTemplate.convertAndSend(queue.getName(), batchMinMax);
				batchMinMax.clear();
			}
		}
	}

	@RabbitListener(queues = "${application.rabbit.queue}", concurrency = "3")
	public void sendList(Map<String, Integer> batchMinMax) {

		Instant start = Instant.now();

		LOGGER.info("Received from queue: {}", batchMinMax);

		List<Rating> newRows = ratingDao.findByOrderIdRange(batchMinMax.get("min"), batchMinMax.get("max"));

		HttpEntity<List<Rating>> payload = new HttpEntity<>(newRows);
		ResponseEntity<String> response = restTemplate.exchange(REST_ENDPOINT, HttpMethod.POST, payload, String.class);

		LOGGER.info("Response received from UDI: [{}], {}", response.getStatusCodeValue(), response.getBody());

		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);

		LOGGER.info("Processing time = {} ms", timeElapsed.toMillis());
	}
}
