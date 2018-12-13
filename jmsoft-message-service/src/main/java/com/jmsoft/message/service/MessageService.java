package com.jmsoft.message.service;

import com.jmsoft.message.vo.MessageVo;

/**
 * 消息服务
 * @author Jack
 *
 */
public interface MessageService {

	/**
	 * 发送
	 * @param messageVo 消息封装类
	 */
	public void sendMessage(MessageVo messageVo);
	
}
