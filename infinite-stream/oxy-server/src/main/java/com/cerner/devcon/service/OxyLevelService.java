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

import com.cerner.devcon.dao.OxyLevelRepository;
import com.cerner.devcon.dto.OxyLevel;

import reactor.core.publisher.Flux;

@Service
public class OxyLevelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OxyLevelService.class);

	private static final long[] SIM_SSNS = { 628162011L, 272517295L, 304182735 };

	private OxyLevelRepository oxyLevelRepository;

	private DateFormat dateFormat;

	private Random random;

	@Autowired
	public OxyLevelService(OxyLevelRepository oxyLevelRepository, DateFormat dateFormat, Random random) {
		this.oxyLevelRepository = oxyLevelRepository;
		this.dateFormat = dateFormat;
		this.random = random;
	}

	public Flux<OxyLevel> findAll() {
		return oxyLevelRepository.findAllLevels();
	}

	public Flux<OxyLevel> findBySsn(long ssn) {
		return oxyLevelRepository.findBySsn(ssn);
	}

	@Scheduled(fixedRate = 30000)
	public void simulateInsertEvent() {
		for (long simSsn : SIM_SSNS) {
			OxyLevel simOxyLevel = new OxyLevel(ObjectId.get(), simSsn,
					Double.valueOf(String.format("%.2f", 85.0 + random.nextInt(15) + random.nextDouble())),
					dateFormat.format(new Date()));
			oxyLevelRepository.save(simOxyLevel).subscribe();
			LOGGER.info("New metric value saved to database: SSN = {}, OxyPerc = {}", simSsn, simOxyLevel.getOxyperc());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
	}
}
