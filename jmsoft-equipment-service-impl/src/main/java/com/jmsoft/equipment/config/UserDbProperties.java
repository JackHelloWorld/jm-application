package com.jmsoft.equipment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.orm.jpa.vendor.Database;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spring.user.database")
public class UserDbProperties {

	private boolean showSql;
	
	private boolean generateDdl;
	
	/**
	 * 设置是否准备事务Hibernate会话的底层JDBC连接，即是否将特定于事务的隔离级别和/或事务的只读标记应用到底层JDBC连接。
	 * */
	private boolean prepareConnection;
	
	//数据库
	private Database database;
	
	//事务超时时间
	private int transactionTimeout;
	
	//强制关闭
	private boolean forceShutdown;
	
}
