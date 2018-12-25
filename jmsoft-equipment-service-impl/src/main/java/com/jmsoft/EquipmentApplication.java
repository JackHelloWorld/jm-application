package com.jmsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

/**
 * 设备模块
 * @author Jack
 *
 */
@SpringBootApplication
@MapperScan("com.jmsoft.equipment.mybatis")
@DubboComponentScan(basePackages = "com.jmsoft.equipment.service")
public class EquipmentApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EquipmentApplication.class, args);
	}
	
}
