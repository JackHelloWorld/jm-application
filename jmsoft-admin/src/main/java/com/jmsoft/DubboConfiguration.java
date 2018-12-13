package com.jmsoft;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.jmsoft.common.data.ConfigData;

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
		consumerConfig.setRetries(0);
		return consumerConfig;
	}
	
	@Bean
	public ProviderConfig providerConfig(){
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setTimeout(6000);
		return providerConfig;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(configData.zookeeperAddress);
		registryConfig.setClient("curator");
		return registryConfig;
	}

}
