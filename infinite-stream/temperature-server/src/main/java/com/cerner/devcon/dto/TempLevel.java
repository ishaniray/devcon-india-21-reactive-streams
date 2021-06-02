package com.cerner.devcon.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "templevel")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TempLevel {

	@Id
	@JsonIgnore
	private ObjectId id;

	long ssn;

	double tempfah;

	String timestamp;
}
