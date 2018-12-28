package com.jmsoft.equipment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spring.datasource.primary")
public class PrimaryDataSourceProperties {

	private String url;
	private String username;
	private String password;
	private int  initialTimeout;
	private String  packagesToScan;
}
