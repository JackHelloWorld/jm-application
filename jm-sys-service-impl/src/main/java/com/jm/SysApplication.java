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
@MapperScan("com.jm.sys.mybatis")
@DubboComponentScan(basePackages = "com.jm.sys.service")
public class SysApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SysApplication.class, args);
	}
	
}
