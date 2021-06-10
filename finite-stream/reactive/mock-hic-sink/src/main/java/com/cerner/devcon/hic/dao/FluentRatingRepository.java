package com.cerner.devcon.hic.dao;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

import com.cerner.devcon.hic.models.Rating;

import reactor.core.publisher.Flux;

@Repository
public class FluentRatingRepository {

	@Autowired
	private R2dbcEntityTemplate r2dbcEntityTemplate;

	public Flux<Rating> getAll() {
		return r2dbcEntityTemplate.select(Rating.class).all();
	}

	public Flux<Rating> findByCrawlInd(String indicator) {
		return r2dbcEntityTemplate.select(Rating.class).matching(query(where("CrawlInd").is(indicator))).all();
	}

}
