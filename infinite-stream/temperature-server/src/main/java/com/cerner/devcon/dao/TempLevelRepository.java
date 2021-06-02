package com.cerner.devcon.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import com.cerner.devcon.dto.TempLevel;

import reactor.core.publisher.Flux;

public interface TempLevelRepository extends ReactiveMongoRepository<TempLevel, ObjectId> {

	@Tailable
	@Query("{ 'ssn': ?0 }")
	Flux<TempLevel> findBySsn(final long ssn);

	@Tailable
	@Query("{}")
	Flux<TempLevel> findAllLevels();
}
