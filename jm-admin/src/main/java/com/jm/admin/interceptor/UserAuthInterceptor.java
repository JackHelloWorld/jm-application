package com.jm.admin.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jm.admin.service.AuthService;
import com.jm.admin.utils.ThreadData;
import com.jm.sys.annotation.ValidateAuth;
import com.jm.sys.annotation.ValidateIgnoreLogin;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultDic;
import com.jm.sys.utils.Tools;

public class UserAuthInterceptor implements HandlerInterceptor{

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private AuthService authService;

	/**
	 * 最后执行！！！
	 */
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception exception)	throws Exception {

	}

	/**
	 * Action执行之后，生成视图之前执行！！
	 */
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,	ModelAndView modelAndView) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {

		if(handler instanceof HandlerMethod){
			
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			
			ValidateIgnoreLogin validateIgnoreLogin = method.getAnnotation(ValidateIgnoreLogin.class);
			if(validateIgnoreLogin != null)
				return true;
			
			String url = request.getRequestURI();
			ValidateAuth validateAuth = method.getAnnotation(ValidateAuth.class);
			if(validateAuth != null){
				if(!validateAuth.validate()) return true;
				if(Tools.notEmpty(validateAuth.value()))
					url = validateAuth.value();
			}
			boolean result = authService.validate(ThreadData.ADMIN_USER.get(),url);
			if(! result){
				ResponseResult.ERROR(ResultDic.NOT_PERMISSION).throwBizException();
			}
		}

		return true;




	}

}
