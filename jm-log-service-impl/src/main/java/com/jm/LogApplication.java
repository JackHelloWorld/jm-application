package com.jm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

/**
 * 日志模块
 * @author Jack
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@DubboComponentScan(basePackages = "com.jm.log.service")
public class LogApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LogApplication.class, args);
	}
	
}
