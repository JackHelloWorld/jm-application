package com.jm;

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
@MapperScan("com.jm.user.mybatis")
@DubboComponentScan(basePackages = "com.jm.user.service")
public class UserApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	
}
