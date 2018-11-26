package com.jm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

/**
 * 消息模块
 * @author Jack
 *
 */
@SpringBootApplication
@DubboComponentScan(basePackages = "com.jm.message.service")
public class MessageApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MessageApplication.class, args);
	}
	
}
