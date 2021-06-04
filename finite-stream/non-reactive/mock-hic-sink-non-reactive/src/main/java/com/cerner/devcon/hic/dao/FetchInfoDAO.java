package com.cerner.devcon.hic.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FetchInfoDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Map<String, Object> fetchInfo(String json) {
		String SQL = "select * from Ratings where Id = %s";
		return jdbcTemplate.queryForMap(String.format(SQL, json));
	}
}
