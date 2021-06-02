package com.cerner.devcon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cerner.devcon.service.MetricsReceiver;

@SpringBootApplication
public class SinkClientApplication {

	private MetricsReceiver metricsReceiver;

	@Autowired
	public SinkClientApplication(MetricsReceiver metricsReceiver) {
		this.metricsReceiver = metricsReceiver;
	}

	@PostConstruct
	public void runApp() {

		metricsReceiver.getMetrics();

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(metricsReceiver::doSomething, 0, 1, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		SpringApplication.run(SinkClientApplication.class, args);
	}
}
