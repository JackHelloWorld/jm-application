package com.jm.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jm.common.annotation.ValidateIgnoreLogin;
import com.jm.common.data.ResponseResult;
import com.jm.message.service.MessageService;
import com.jm.message.vo.MessageVo;

@RestController
@RequestMapping("test")
public class TestController {

	@Reference
	MessageService messageService;
	
	@Value("${kafka.consumer.topic}")
	private String topic;
	
	@GetMapping("send")
	@ValidateIgnoreLogin
	public ResponseResult send(String message){
		
		MessageVo messageVo = new MessageVo(topic,"test",message);
		
		messageService.sendMessage(messageVo);
		return ResponseResult.SUCCESSM("操作成功");
	}
}
