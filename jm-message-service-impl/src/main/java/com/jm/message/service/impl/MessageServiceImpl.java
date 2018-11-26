package com.jm.message.service.impl;

import javax.annotation.Resource;

import org.springframework.kafka.core.KafkaTemplate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jm.message.service.MessageService;
import com.jm.message.vo.MessageVo;
import com.jm.sys.service.BaseService;

@Service
public class MessageServiceImpl implements MessageService{

	@Reference
	BaseService baseService;
	
	@Resource
	KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void sendMessage(MessageVo messageVo) {
		
		kafkaTemplate.send(messageVo.getTopic(), messageVo.getKey(), messageVo.getData());
		
	}
	
	
}
