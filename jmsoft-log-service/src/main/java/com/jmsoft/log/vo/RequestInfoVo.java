package com.jmsoft.log.vo;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求日志信息
 * @author Jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfoVo implements Serializable{
	
	private static final long serialVersionUID = 6771447967059526753L;
	
	/**请求时间*/
	private String timer;
	
	/**操作控制器类*/
	private String controller;
	
	/**操作控制函数*/
	private String method;
	
	/**请求地址*/
	private String requestUrl;
	
	/**请求参数*/
	private Map<String, String> params;
	
	/**请求头部信息*/
	private Map<String, String> headers;
	
}
