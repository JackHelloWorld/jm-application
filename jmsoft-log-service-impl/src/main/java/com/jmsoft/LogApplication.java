package com.jmsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

/**
 * 日志模块
 * @author Jack
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {LogInterceptorConfig.class}))
@DubboComponentScan(basePackages = "com.jmsoft.log.service")
public class LogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogApplication.class, args);
	}
	
}
