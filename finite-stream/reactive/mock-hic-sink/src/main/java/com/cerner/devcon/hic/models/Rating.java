package com.cerner.devcon.hic.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Rating implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@JsonProperty(index = 2)
	private int orderId;

	@JsonProperty(index = 3)
	private int custId;

	@JsonProperty(index = 4)
	private int restaurantId;

	@JsonProperty(index = 5)
	private int deliveryAgentId;

	@JsonProperty(index = 6)
	private int foodRating;

	@JsonProperty(index = 7)
	private int deliveryRating;

	@JsonProperty(index = 8)
	private String crawlInd;

}
