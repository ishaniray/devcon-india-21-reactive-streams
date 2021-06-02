package com.cerner.devcon.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TempLevel {

	long ssn;

	double tempfah;

	String timestamp;
}
