package com.jmsoft.log.service;

/**
 * 日志服务
 * @author Jack
 *
 */
public interface LogService {

	/**
	 * 消息日志
	 * @param message 日志内容
	 */
	public void info(Object message);
	
	/**
	 * 错误日志
	 * @param message 日志内容
	 */
	public void error(Object message);
	
	/**
	 * 警告日志
	 * @param message 日志内容
	 */
	public void warn(Object message);
	
	/**
	 * debug日志
	 * @param message 日志内容
	 */
	public void debug(Object message);
	
}
