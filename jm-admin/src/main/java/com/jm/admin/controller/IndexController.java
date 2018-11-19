package com.jm.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jm.sys.annotation.ValidateIgnoreLogin;
import com.jm.sys.web.BaseController;


@RestController
public class IndexController extends BaseController{

	private static final long serialVersionUID = 3199591341482656712L;

	@GetMapping("/")
	@ValidateIgnoreLogin
	public String index(){
		return "The service is running";
	}
	

}
