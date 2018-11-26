package com.jm.sys.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.jm.message.service.MessageService;
import com.jm.message.vo.MessageVo;
import com.jm.sys.annotation.KafkaLog;
import com.jm.sys.utils.ControllerUtils;
import com.jm.sys.utils.Tools;

@Aspect  
@Component 
public class LogInterceptor implements Ordered{

	@Override
	public int getOrder() {
		return 0;
	}
	
	@Reference
	MessageService messageService;
	
	@Around("@annotation(kafkaLog)")
	public Object Log(ProceedingJoinPoint joinPoint,KafkaLog kafkaLog){
		Object retVal = null;
		try {
			if (joinPoint == null) { 
				return null;
			}
			JSONObject message = new JSONObject();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String ip = ControllerUtils.getIpAddress(request);

			//获取方法参数
			Enumeration<String> eParams = request.getParameterNames();
			while (eParams.hasMoreElements()) {
				String key = eParams.nextElement();
				String value = request.getParameter(key);
				message.put(key, value);
			}

			//获取header参数
			Enumeration<?> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				String value = request.getHeader(key);
				//时间戳单位统一
				if ("timestamp".equals(key) && Tools.notEmpty(value) && value.length() > 10) {
					value = value.substring(0, 10);
				}
				message.put(key, value);
			}
			String requestURL=request.getRequestURL().toString();
			message.put("requestURL", requestURL);message.put("class", joinPoint.getTarget().getClass().getName());
			message.put("request_method", joinPoint.getSignature().getName());
			message.put("ip", ip);
			message.put("systemName", "all");
			
			messageService.sendMessage(new MessageVo("topic.sys.log", "log", message.toJSONString()));

			retVal =joinPoint.proceed();
		} catch (Throwable throwable) {
			throwable.getMessage();
		}
		return retVal;
	}



}
