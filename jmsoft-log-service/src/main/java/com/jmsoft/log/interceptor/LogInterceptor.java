package com.jmsoft.log.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.jmsoft.log.service.LogService;
import com.jmsoft.log.vo.RequestInfoVo;

/**
 * 日志拦截器
 * @author Jack
 *
 */
public class LogInterceptor implements HandlerInterceptor{
	
	@Reference
	LogService logService;
	
	private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	
	/**
	 * 最后执行！！！
	 */
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception exception)	throws Exception {
		if(exception != null){
			logService.error(exception);
		}else{
			
		}
	}

	/**
	 * Action执行之后，生成视图之前执行！！
	 */
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,	ModelAndView modelAndView) throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
			RequestInfoVo requestInfo = new RequestInfoVo();
			HandlerMethod method = (HandlerMethod)handler;
			requestInfo.setTimer(sdf.get().format(new Date()));
			String className = (method.getBean().getClass().getName());
			requestInfo.setController(method.getBean().getClass().getName().concat(className.trim().substring(className.lastIndexOf('.')+1, className.length())));
			requestInfo.setMethod(method.getMethod().getName());
			Enumeration<String> names = request.getParameterNames();
			
			Map<String, String> params = new HashMap<>();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				params.put(name, request.getParameter(name));
			}
			requestInfo.setParams(params);
			
			Enumeration<String> headers = request.getHeaderNames();
			Map<String, String> headersM = new HashMap<>();
			while (headers.hasMoreElements()) {
				String header = headers.nextElement();
				headersM.put(header,request.getHeader(header));
			}
			requestInfo.setHeaders(headersM);
			requestInfo.setRequestUrl(request.getRequestURI());
			
			logService.info(JSONObject.toJSONString(requestInfo));
		}
		return true;
	}

}
