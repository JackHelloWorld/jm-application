package com.jm.common.exception;

import com.jm.common.data.ResponseResult;

/**
 * 未知异常
 * @author Jack
 *
 */
public class NoException extends Exception{

	private static final long serialVersionUID = -1196851528373507875L;
	
	private ResponseResult responseResult;
	
	public NoException(ResponseResult result){
		super(result.getMsg());
		this.responseResult = result;
	}
	
	public NoException(String message){
		super(message);
	}

	public ResponseResult getResponseResult() {
		return responseResult;
	}
	
	public static void throwException(ResponseResult responseResult) throws NoException{
		throw new NoException(responseResult);
	}

}
