package com.jm.sys.exception;

import com.jm.sys.data.ResponseResult;

/**
 * 参数异常
 * @author Jack
 *
 */
public class ParamException extends Exception{

	private static final long serialVersionUID = -1196851528373507875L;
	
	private ResponseResult responseResult;
	
	public ParamException(ResponseResult result){
		super(result.getMsg());
		this.responseResult = result;
	}
	
	public ParamException(String message){
		super(message);
	}

	public ResponseResult getResponseResult() {
		return responseResult;
	}
	
	public static void throwException(ResponseResult responseResult) throws ParamException{
		throw new ParamException(responseResult);
	}

}
