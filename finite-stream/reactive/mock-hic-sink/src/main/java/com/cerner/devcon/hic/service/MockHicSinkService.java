package com.cerner.devcon.hic.service;

import java.time.ZonedDateTime;
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
import org.springframework.web.reactive.function.client.WebClient;

import com.cerner.devcon.hic.constants.CrawlInd;
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

	@Scheduled(fixedRate = 10000)
	public void insertRowsIntoDbAndPublishMsg() {

		for (int i = 0; i < 10; ++i) {

			int orderId = atomicInteger.getAndIncrement();
			int customerId = 100 + random.nextInt(900);
			int restaurantId = 1000 + random.nextInt(1000);
			int deliveryAgentId = 2000 + random.nextInt(1000);
			int foodRating = 1 + random.nextInt(5);
			int deliveryRating = 1 + random.nextInt(5);
			String crawlInd = CrawlInd.NOT_STARTED.getIndicator();

			Rating rating = new Rating(null, orderId, customerId, restaurantId, deliveryAgentId, foodRating,
					deliveryRating, crawlInd);

			ratingRepository.save(rating).subscribe();
		}

		rabbitTemplate.convertAndSend(queue.getName(), "10 rows inserted at " + ZonedDateTime.now().toString() + ".");
	}

	@RabbitListener(queues = "${application.rabbit.queue}", concurrency = "3")
	public void sendFlux(String message) {

		LOGGER.info(message);

		Flux<Rating> newRows = ratingRepository.findByCrawlInd(CrawlInd.NOT_STARTED.getIndicator());

		Mono<String> udiResponse = WebClient.create("http://localhost:7071").method(HttpMethod.POST).uri("/consume")
				.body(newRows, Rating.class).retrieve().bodyToMono(String.class);

		udiResponse.doOnNext(LOGGER::info).subscribe();
	}
}
