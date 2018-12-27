package com.jmsoft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jmsoft.wxservice.interceptor.AccessTokenInterceptor;
import com.jmsoft.wxservice.interceptor.UserInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public UserInterceptor userInterceptor(){
		return new UserInterceptor();
	}
	
	@Bean
	public AccessTokenInterceptor accessTokenInterceptor(){
		return new AccessTokenInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor()).excludePathPatterns("/error","/swagger*","/swagger-resources/**");
		registry.addInterceptor(accessTokenInterceptor()).excludePathPatterns("/error","/swagger*","/swagger-resources/**");
	}

}
