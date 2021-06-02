package com.cerner.devcon.service;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cerner.devcon.dao.TempLevelRepository;
import com.cerner.devcon.dto.TempLevel;

import reactor.core.publisher.Flux;

@Service
public class TempLevelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TempLevelService.class);

	private static final long[] SIM_SSNS = { 628162011L, 272517295L, 304182735 };

	private TempLevelRepository tempLevelRepository;

	private DateFormat dateFormat;

	private Random random;

	@Autowired
	public TempLevelService(TempLevelRepository tempLevelRepository, DateFormat dateFormat, Random random) {
		this.tempLevelRepository = tempLevelRepository;
		this.dateFormat = dateFormat;
		this.random = random;
	}

	public Flux<TempLevel> findAll() {
		return tempLevelRepository.findAllLevels();
	}

	public Flux<TempLevel> findBySsn(long ssn) {
		return tempLevelRepository.findBySsn(ssn);
	}

	@Scheduled(fixedRate = 30000)
	public void simulateInsertEvent() {
		for (long simSsn : SIM_SSNS) {
			TempLevel simTempLevel = new TempLevel(ObjectId.get(), simSsn,
					Double.valueOf(String.format("%.2f", 97.0 + random.nextInt(7) + random.nextDouble())),
					dateFormat.format(new Date()));
			tempLevelRepository.save(simTempLevel).subscribe();
			LOGGER.info("New metric value saved to database: SSN = {}, TempFah = {}", simSsn,
					simTempLevel.getTempfah());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
	}
}
