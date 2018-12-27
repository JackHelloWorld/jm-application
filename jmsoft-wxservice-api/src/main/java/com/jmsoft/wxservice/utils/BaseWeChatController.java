package com.jmsoft.wxservice.utils;

import com.jmsoft.common.web.BaseController;
import com.jmsoft.user.vo.LoginUserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseWeChatController extends BaseController{

	private static final long serialVersionUID = 2708622649554257953L;

	/**
	 * 获取登录用户
	 * @return
	 */
	protected LoginUserVo getUser(){
		log.info("get user info");
		return ThreadData.LOGIN_USER.get();
	}
	
	
}
