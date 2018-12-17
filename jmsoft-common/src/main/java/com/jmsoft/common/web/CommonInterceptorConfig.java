package com.jmsoft.common.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jmsoft.common.interceptor.SysInterceptor;

@Configuration
public class CommonInterceptorConfig implements WebMvcConfigurer {

	@Bean
	public SysInterceptor sysInterceptor(){
		return new SysInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sysInterceptor()).excludePathPatterns("/error");
	}

}
