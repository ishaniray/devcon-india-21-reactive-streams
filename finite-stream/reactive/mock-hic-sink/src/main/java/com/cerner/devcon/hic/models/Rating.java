package com.cerner.devcon.hic.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Rating implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int orderId;

	private int custId;

	private int restaurantId;

	private int deliveryAgentId;

	private int foodRating;

	private int deliveryRating;

	private String crawlInd;

}
