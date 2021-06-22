package com.cerner.devcon.hic.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.cerner.devcon.hic.dao.RatingRepository;
import com.cerner.devcon.hic.models.Rating;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MockHicSinkService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockHicSinkService.class);

	private AtomicInteger atomicInteger;

	private Random random;

	private RatingRepository ratingRepository;

	private Queue queue;

	private RabbitTemplate rabbitTemplate;

	@Autowired
	public MockHicSinkService(AtomicInteger atomicInteger, Random random, RatingRepository ratingRepository,
			Queue queue, RabbitTemplate rabbitTemplate) {
		this.atomicInteger = atomicInteger;
		this.random = random;
		this.ratingRepository = ratingRepository;
		this.queue = queue;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Scheduled(fixedRate = 60000)
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

			ratingRepository.save(rating).subscribe();

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
	public void sendFlux(Map<String, Integer> batchMinMax) {

		Instant start = Instant.now();

		LOGGER.info("Received from queue: {}", batchMinMax);

		Flux<Rating> newRowsWithBackpressureBuffer = ratingRepository.findByOrderIdRange(batchMinMax.get("min"),
				batchMinMax.get("max"));

		Mono<String> udiResponse = WebClient.create("http://localhost:7071").method(HttpMethod.POST).uri("/consume")
				.body(BodyInserters.fromPublisher(newRowsWithBackpressureBuffer, Rating.class)).retrieve()
				.bodyToMono(String.class);

		udiResponse.doOnNext(response -> LOGGER.info("Response from UDI: {}", response)).subscribe();

		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);

		LOGGER.info("Processing time = {} ms", timeElapsed.toMillis());
	}
}
