package com.jmsoft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jmsoft.admin.interceptor.UserAuthInterceptor;
import com.jmsoft.admin.interceptor.UserInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public UserInterceptor userInterceptor(){
		return new UserInterceptor();
	}
	
	@Bean
	public UserAuthInterceptor userAuthInterceptor(){
		return new UserAuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor()).excludePathPatterns("/error","/swagger*","/swagger-resources/**");
		registry.addInterceptor(userAuthInterceptor()).excludePathPatterns("/error","/swagger*","/swagger-resources/**");
	}

}
