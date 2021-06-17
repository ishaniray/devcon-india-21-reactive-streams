package com.cerner.devcon.hic.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table("Ratings")
@Data
@AllArgsConstructor
public class Rating {

	@Id
	private Integer id;

	@Column("OrderId")
	private Integer orderId;

	@Column("CustId")
	private Integer custId;

	@Column("RestaurantId")
	private Integer restaurantId;

	@Column("DeliveryAgentId")
	private Integer deliveryAgentId;

	@Column("FoodRating")
	private Integer foodRating;

	@Column("DeliveryRating")
	private Integer deliveryRating;
}
