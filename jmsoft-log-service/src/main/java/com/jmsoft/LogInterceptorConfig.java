package com.jmsoft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jmsoft.log.interceptor.LogInterceptor;

@Configuration
public class LogInterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public LogInterceptor logInterceptor(){
		return new LogInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor()).excludePathPatterns("/error");
	}

}
