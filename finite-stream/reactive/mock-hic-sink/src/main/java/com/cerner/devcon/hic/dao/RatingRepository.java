package com.cerner.devcon.hic.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.cerner.devcon.hic.models.Rating;

import reactor.core.publisher.Flux;

@Repository
public interface RatingRepository extends ReactiveCrudRepository<Rating, Integer> {

	@Query("SELECT * FROM Ratings WHERE CrawlInd = :indicator")
	Flux<Rating> findByCrawlInd(String indicator);
}
