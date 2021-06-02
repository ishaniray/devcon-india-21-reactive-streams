package com.cerner.devcon.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import com.cerner.devcon.dto.OxyLevel;

import reactor.core.publisher.Flux;

public interface OxyLevelRepository extends ReactiveMongoRepository<OxyLevel, ObjectId> {

	@Tailable
	@Query("{ 'ssn': ?0 }")
	Flux<OxyLevel> findBySsn(final long ssn);

	@Tailable
	@Query("{}")
	Flux<OxyLevel> findAllLevels();
}
