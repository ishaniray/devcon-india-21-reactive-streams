package com.cerner.devcon.config;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public SimpleDateFormat getDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf;
	}

	@Bean
	public Random getRandom() {
		return new Random();
	}
}
