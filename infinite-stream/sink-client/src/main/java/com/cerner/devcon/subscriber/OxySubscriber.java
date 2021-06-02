package com.cerner.devcon.subscriber;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import reactor.core.publisher.BaseSubscriber;

@Component
public class OxySubscriber<OxyLevel> extends BaseSubscriber<OxyLevel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OxySubscriber.class);

	private static final int LIMIT = 2;

	private int consumed = 0;

	@Override
	public void hookOnSubscribe(Subscription subscription) {
		LOGGER.info("Requesting {} items.", LIMIT);
		request(LIMIT);
	}

	@Override
	public void hookOnNext(OxyLevel oxyLevel) {

		LOGGER.info("Received: " + oxyLevel.toString());

		consumed++;

		if (consumed == LIMIT) {
			consumed = 0;
			LOGGER.info("Requesting {} more items.", LIMIT);
			request(LIMIT);
		}
	}

	@Override
	public void hookOnError(Throwable error) {
		LOGGER.error("An error occured: " + error);
	}

	@Override
	public void hookOnComplete() {
		LOGGER.info("End of stream.");
	}
}
