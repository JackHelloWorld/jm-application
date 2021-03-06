package com.jmsoft.common.data;

import java.io.Serializable;

import com.jmsoft.common.exception.BizException;
import com.jmsoft.common.exception.NoException;
import com.jmsoft.common.exception.ParamException;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 结果处理工具
 * @author liuJack
 *
 */
@Data
@AllArgsConstructor
public final class ResponseResult implements Serializable{

	private static final long serialVersionUID = 2012087081741204962L;
	
	@ApiModelProperty(value="返回结果消息",required=true)
	/**结果提示信息取值字符*/
	private String msg;
	
	/**结果编码取值字符--详情见ResultDic.class*/
	@ApiModelProperty(value="返回结果编码",required=true)
	private Integer code;
	
	/**结果附带数据取值字符*/
	@ApiModelProperty(value="返回结果数据")
	private Object data;
	
	/**结果状态*/
	@ApiModelProperty(value="是否成功\n {true:成功,false:失败}",required=true)
	private Boolean status;
	
	/**
	 * 操作成功,并返回数据
	 * @param data 需要返回的数据
	 * @return
	 */
	public static final ResponseResult SUCCESS(Object data){
		ResponseResult tools = new ResponseResult(ResultDic.SUCCESS.getMsg(), 
				ResultDic.SUCCESS.getCode(), data, ResultDic.SUCCESS.getStatus());
		return tools;
	}
	
	/**
	 * 操作成功,并返回数据及操作提示语
	 * @param data 需要返回的数据
	 * @return
	 */
	public static final ResponseResult SUCCESS(String message,Object data){
		ResponseResult tools = new ResponseResult(message, ResultDic.SUCCESS.getCode(), data, 
				ResultDic.SUCCESS.getStatus());
		return tools;
	}

	/**
	 * 操作成功,无返回数据
	 * @return
	 */
	public static final ResponseResult SUCCESS(){
		ResponseResult tools = new ResponseResult(ResultDic.SUCCESS.getMsg(), 
				ResultDic.SUCCESS.getCode(), 
				null, ResultDic.SUCCESS.getStatus());
		return tools;
	}
	
	/**
	 * 操作成功,返回提示语
	 * @return
	 */
	public static final ResponseResult SUCCESSM(String message){
		ResponseResult tools = new ResponseResult(message, 
				ResultDic.SUCCESS.getCode(), 
				null, 
				ResultDic.SUCCESS.getStatus());
		return tools;
	}
	

	/**
	 * 操作错误,并返回数据
	 * @param dic 错误字典
	 * @param error 需要返回的数据
	 * @return
	 */
	public static final ResponseResult ERROR(ResultDic dic,Object error){
		ResponseResult tools = new ResponseResult(dic.getMsg(), dic.getCode(), error, dic.getStatus());
		return tools;
	}

	/**
	 * 操作错误,无返回数据
	 * @param dic 错误字典
	 * @return
	 */
	public static final ResponseResult ERROR(ResultDic dic){
		ResponseResult tools = new ResponseResult(dic.getMsg(), dic.getCode(), null, dic.getStatus());
		return tools;
	}
	

	/**
	 * 自定义操作错误信息,并返回数据
	 * @param code 错误编码
	 * @param msg 错误信息
	 * @param data 返回的数据
	 * @return
	 */
	public static final ResponseResult DIY_ERROR(ResultCode code,String msg,Object data){
		ResponseResult tools = new ResponseResult(msg, code.getCode(), data, false);
		return tools;
	}
	
	/**
	 * 自定义操作错误信息,无返回数据
	 * @param code 错误编码
	 * @param msg 错误信息
	 * @return
	 */
	public static final ResponseResult DIY_ERROR(ResultCode code,String msg){
		ResponseResult tools = new ResponseResult(msg, code.getCode(), null, false);
		return tools;
	}

	public static ResponseResult ERROR(Integer code, String msg) {
		ResponseResult tools = new ResponseResult(msg, code, null, false);
		return tools;
	}
	
	/**
	 * 抛出业务异常
	 * @throws BizException
	 */
	public void throwBizException() throws BizException{
		throw new BizException(this);
	}
	
	/**
	 * 抛出参数异常
	 * @throws ParamException
	 */
	public void throwParamException() throws ParamException{
		throw new ParamException(this);
	}
	
	/**
	 * 抛出未知错误
	 * @throws Exception
	 */
	public void throwNoException() throws NoException{
		throw new NoException(this);
	}
	

}

