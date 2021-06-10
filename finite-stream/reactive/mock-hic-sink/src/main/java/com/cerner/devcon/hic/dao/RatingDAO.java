package com.cerner.devcon.hic.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.cerner.devcon.hic.models.Rating;

import reactor.core.publisher.Flux;

@Repository
public interface RatingDAO extends R2dbcRepository<Rating, Integer> {

	@Query("SELECT * FROM Ratings WHERE CrawlInd = @indicator")
	Flux<Rating> findByCrawlInd(String indicator);

}
