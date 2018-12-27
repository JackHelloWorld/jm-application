package com.jmsoft.wxservice.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jmsoft.wxservice.service.AccessTokenService;

public class AccessTokenInterceptor  extends HandlerInterceptorAdapter{
	
  @Resource
  AccessTokenService accessTokenService;
  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    this.accessTokenService.gainAccessToken();
    return true;
  }
  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception{
  }
 
}
