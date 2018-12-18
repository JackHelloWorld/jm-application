package com.jmsoft.admin.interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jmsoft.admin.utils.ThreadData;
import com.jmsoft.common.annotation.ValidateIgnoreLogin;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultDic;
import com.jmsoft.user.vo.AdminUserDbVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInterceptor implements HandlerInterceptor{

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**后台用户登录超时时间(分)*/
	@Value("${user.web.login.timeout}")
	private Long loginTimeout;

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

			String token = request.getHeader("token");
			if(token != null){

				log.info("login-token:{}",token);

				AdminUserDbVo adminUser = (AdminUserDbVo) redisTemplate.opsForValue().get(token);

				if(adminUser == null)
					ResponseResult.ERROR(ResultDic.NOT_LOGIN).throwBizException();

				//token续期
				redisTemplate.opsForValue().set(token, adminUser, loginTimeout, TimeUnit.MINUTES);

				ThreadData.ADMIN_USER.set(adminUser);

			}else{
				ResponseResult.ERROR(ResultDic.NOT_LOGIN).throwBizException();
			}

		}



		return true;




	}

}
