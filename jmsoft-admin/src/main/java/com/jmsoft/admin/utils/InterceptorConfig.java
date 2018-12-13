package com.jmsoft.admin.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jmsoft.admin.interceptor.UserAuthInterceptor;
import com.jmsoft.admin.interceptor.UserInterceptor;
import com.jmsoft.sys.interceptor.SysInterceptor;

@Configuration
@SuppressWarnings("deprecation")
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	public SysInterceptor sysInterceptor(){
		return new SysInterceptor();
	}
	
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
		registry.addInterceptor(sysInterceptor()).excludePathPatterns("/error");
		registry.addInterceptor(userInterceptor()).excludePathPatterns("/error");
		registry.addInterceptor(userAuthInterceptor()).excludePathPatterns("/error");
		super.addInterceptors(registry);
	}

}
