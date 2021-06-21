package com.cerner.devcon.subscriber;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.BaseSubscriber;

public class RatingSubscriber<Rating> extends BaseSubscriber<Rating> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RatingSubscriber.class);

	private int consumed = 0;
	private int limit = 1;

	@Override
	public void hookOnSubscribe(Subscription subscription) {
		request(limit);
	}

	@Override
	public void hookOnNext(Rating rating) {

		LOGGER.info("Received {} at {}", rating.toString(), ZonedDateTime.now().toString());

		consumed++;

		if (consumed == limit) {
			consumed = 0;
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			LOGGER.info("Requesting for {} items.", limit);
			request(limit);
		}
	}

	@Override
	public void hookOnError(Throwable error) {
		LOGGER.error("An error occured: {}", error.getMessage());
	}

	@Override
	public void hookOnComplete() {
		LOGGER.info("End of stream.");
	}
}
