package com.jmsoft.wxservice.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置
 * @author Jack
 *
 */
@Configuration
public class WeChatConfig {

	@Value("${url.code2session}")
	public String code2SessionUrl;
	
	@Value("${wechat.appsecret}")
	public String appsecret;
	
	@Value("${wechat.appid}")
	public String appid;
	
	
	
}
