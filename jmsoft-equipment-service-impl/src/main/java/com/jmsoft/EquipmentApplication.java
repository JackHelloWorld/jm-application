package com.jmsoft;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.github.pagehelper.PageHelper;

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
	
	
	//配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
