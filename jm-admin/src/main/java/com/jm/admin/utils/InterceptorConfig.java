package com.jm.admin.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jm.admin.interceptor.UserAuthInterceptor;
import com.jm.admin.interceptor.UserInterceptor;
import com.jm.log.interceptor.LogInterceptor;
import com.jm.sys.interceptor.SysInterceptor;

@Configuration
@SuppressWarnings("deprecation")
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	public SysInterceptor sysInterceptor(){
		return new SysInterceptor();
	}
	
	@Bean
	public LogInterceptor logInterceptor(){
		return new LogInterceptor();
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
		registry.addInterceptor(logInterceptor()).excludePathPatterns("/error");
		registry.addInterceptor(userInterceptor()).excludePathPatterns("/error");
		registry.addInterceptor(userAuthInterceptor()).excludePathPatterns("/error");
		super.addInterceptors(registry);
	}

}
