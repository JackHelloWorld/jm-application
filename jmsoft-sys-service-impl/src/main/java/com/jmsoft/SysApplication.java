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
@MapperScan("com.jmsoft.sys.mybatis")
@DubboComponentScan(basePackages = "com.jmsoft.sys.service")
public class SysApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SysApplication.class, args);
	}

}
