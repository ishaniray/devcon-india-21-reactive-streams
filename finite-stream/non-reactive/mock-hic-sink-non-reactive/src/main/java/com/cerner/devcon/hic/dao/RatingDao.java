package com.cerner.devcon.hic.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cerner.devcon.hic.models.Rating;

@Repository
public class RatingDao {

	private static final RowMapper<Rating> RATING_MAPPER = new BeanPropertyRowMapper<>(Rating.class);

	private static final String INSERT_ROW_SQL = "INSERT [dbo].[Ratings] ([OrderId], [CustId], [RestaurantId], [DeliveryAgentId], [FoodRating], [DeliveryRating]) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String FIND_BY_ORDER_ID_SQL = "SELECT * FROM Ratings WHERE OrderId BETWEEN ? AND ?";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public RatingDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int insertRow(Rating rating) {
		return jdbcTemplate.update(INSERT_ROW_SQL, rating.getOrderId(), rating.getCustId(), rating.getRestaurantId(),
				rating.getDeliveryAgentId(), rating.getFoodRating(), rating.getDeliveryRating());
	}

	public List<Rating> findByOrderIdRange(int min, int max) {
		return jdbcTemplate.query(FIND_BY_ORDER_ID_SQL, RATING_MAPPER, min, max);
	}
}
