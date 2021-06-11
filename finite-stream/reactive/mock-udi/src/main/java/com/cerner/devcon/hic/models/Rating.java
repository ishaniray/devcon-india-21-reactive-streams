package com.cerner.devcon.hic.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rating {

	private Integer id;

	private Integer orderId;

	private Integer custId;

	private Integer restaurantId;

	private Integer deliveryAgentId;

	private Integer foodRating;

	private Integer deliveryRating;

	private String crawlInd;
}
