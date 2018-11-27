package com.jm.log.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jm.log.service.LogService;
import com.jm.message.service.MessageService;
import com.jm.message.vo.MessageVo;

@Service
public class LogServiceImpl implements LogService{

	@Value("${kafka.log.topic}")
	private String topic;
	
	@Reference
	MessageService messageService;
	
	@Override
	public void info(Object message) {
		MessageVo messageVo = new MessageVo(topic, "info", message);
		messageService.sendMessage(messageVo);
	}

	@Override
	public void error(Object message) {
		MessageVo messageVo = new MessageVo(topic, "error", message);
		messageService.sendMessage(messageVo);
	}

	@Override
	public void warn(Object message) {
		MessageVo messageVo = new MessageVo(topic, "warn", message);
		messageService.sendMessage(messageVo);
	}

	@Override
	public void debug(Object message) {
		MessageVo messageVo = new MessageVo(topic, "debug", message);
		messageService.sendMessage(messageVo);
	}

}
