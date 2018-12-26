package com.jmsoft.wxservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsoft.common.annotation.ValidateIgnoreLogin;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(description="系统描述",tags="系统",position=1)
public class IndexController extends BaseController{

	private static final long serialVersionUID = 3199591341482656712L;

	@GetMapping("/")
	@ValidateIgnoreLogin
	@ApiOperation("系统服务查看")
	public ResponseResult index(){
		Map<String, Object> result = new HashMap<>();
		result.put("document", "/swagger-ui.html");
		return ResponseResult.SUCCESS("The service is running", result);
	}
	

}
