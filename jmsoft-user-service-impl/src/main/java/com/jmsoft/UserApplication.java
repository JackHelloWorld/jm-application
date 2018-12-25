package com.jmsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

/**
 * 用户模块
 * @author Jack
 *
 */
@SpringBootApplication
@MapperScan("com.jmsoft.user.mybatis")
@DubboComponentScan(basePackages = "com.jmsoft.user.service")
public class UserApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	
}
