package com.jmsoft.wxservice.job;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.jmsoft.common.exception.BizException;
import com.jmsoft.wxservice.service.AccessTokenService;

@Configuration
public class JobService {

	@Resource
	AccessTokenService accessTokenService;
	
	 @Scheduled(cron = "0 0 */2 * * ? ")
	 public void accessTokenJob() throws BizException {
		 accessTokenService.gainAccessToken();
	 }
	
}
