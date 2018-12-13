package com.jmsoft.common.data;

/**
 * 错误代码字典
 * @author liuJack
 *
 */
public enum ResultDic {

	/**请求成功*/
	SUCCESS(0,"SUCCESS",true),

	/**数据传输有误*/
	DATA_WRONG(100,"数据传输有误",false),
	
	/**未登录*/
	NOT_LOGIN(1,"登录已过期",false),
	
	/**无权操作*/
	NOT_PERMISSION(12,"无权操作",false),
	
	/**系统错误*/
	SYS_ERROR(500,"网络错误",false),
	
	/**未知错误*/
	NON_ERROR(9,"未知错误",false);
	
	private Integer code;
	
	private String msg;
	
	private boolean status;
	
	ResultDic(Integer code,String msg,boolean status){
		this.code = code;
		this.msg = msg;
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public boolean getStatus() {
		return status;
	}
}
