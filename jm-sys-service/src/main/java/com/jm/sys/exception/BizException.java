package com.jm.sys.exception;

import com.jm.sys.data.ResponseResult;

/**
 * 业务异常
 * @author Jack
 *
 */
public class BizException extends Exception{

	private static final long serialVersionUID = -1196851528373507875L;
	
	private ResponseResult responseResult;
	
	public BizException(ResponseResult result){
		super(result.getMsg());
		this.responseResult = result;
	}
	
	public BizException(String message){
		super(message);
	}

	public ResponseResult getResponseResult() {
		return responseResult;
	}
	
	public static void throwException(ResponseResult responseResult) throws BizException {
		throw new BizException(responseResult);
	}

}
