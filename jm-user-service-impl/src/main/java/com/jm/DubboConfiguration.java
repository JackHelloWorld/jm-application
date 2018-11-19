package com.jm;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.jm.sys.data.ConfigData;

@Configuration
public class DubboConfiguration {

	@Resource
	ConfigData configData;
	
	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(configData.dubboName);
		applicationConfig.setQosPort(configData.dubboQosPort);
		return applicationConfig;
	}
	
	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setTimeout(30000);
		return consumerConfig;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(configData.zookeeperAddress);
		registryConfig.setClient("curator");
		return registryConfig;
	}
	
	@Bean
	public ProtocolConfig protocolConfig(){
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setName(configData.dubboProtocolName);
		protocolConfig.setPort(configData.dubboProtocolPort);
		protocolConfig.setDefault(true);
		return protocolConfig;
	}
}
