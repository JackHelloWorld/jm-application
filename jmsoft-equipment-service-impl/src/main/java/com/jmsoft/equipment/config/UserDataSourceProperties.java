package com.jmsoft.equipment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spring.datasource.user")
public class UserDataSourceProperties {

	private String url;
	private String username;
	private String password;
	private int  initialTimeout;
	private String  packagesToScan;
}
