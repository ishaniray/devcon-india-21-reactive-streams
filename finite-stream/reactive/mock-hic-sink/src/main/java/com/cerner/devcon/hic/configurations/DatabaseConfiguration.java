package com.cerner.devcon.hic.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.r2dbc.mssql.MssqlConnectionConfiguration;
import io.r2dbc.mssql.MssqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@Configuration
@EnableR2dbcRepositories
//(basePackages = "com.cerner.devcon.hic.dao")
class DatabaseConfiguration { // extends AbstractR2dbcConfiguration

	@Value("${spring.data.mssql.host}")
	private String host;

	@Value("${spring.data.mssql.port}")
	private int port;

	@Value("${spring.data.mssql.database}")
	private String database;

	@Value("${spring.data.mssql.username}")
	private String username;

	@Value("${spring.data.mssql.password}")
	private String password;

	/*
	 * @Override public ConnectionFactory connectionFactory() {
	 * 
	 * 
	 * return ConnectionFactories.get("r2dbc:mssql://localhost:1433/devcon21"); new
	 * MssqlConnectionFactory(MssqlConnectionConfiguration.builder().
	 * host(host).port(port)
	 * .database(database).username(username).password(password).build()); }
	 */
}