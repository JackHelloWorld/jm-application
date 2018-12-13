package com.jmsoft.common.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultDic;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.common.exception.ParamException;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理
 * @author Jack
 *
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionHandle {

	@ExceptionHandler(BizException.class)
	public ResponseResult bizException(BizException bizException){
		log.error("bizException", bizException);
		return bizException.getResponseResult();
	}
	
	@ExceptionHandler(ParamException.class)
	public Object bizException(ParamException paramException){
		log.error("paramException", paramException);
		return paramException.getResponseResult();
	}
	
	@ExceptionHandler(RuntimeException.class)
	public Object runtimeException(RuntimeException runtimeException){
		log.error("runtimeException", runtimeException);
		return ResponseResult.ERROR(ResultDic.SYS_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public Object exception(Exception exception){
		log.error("exception", exception);
		return ResponseResult.ERROR(ResultDic.NON_ERROR);
	}
	
}
