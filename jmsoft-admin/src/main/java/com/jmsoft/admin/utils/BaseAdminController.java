package com.jmsoft.admin.utils;

import com.jmsoft.common.web.BaseController;
import com.jmsoft.user.vo.AdminUserDbVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseAdminController extends BaseController{

	private static final long serialVersionUID = 2708622649554257953L;

	/**
	 * 获取登录用户
	 * @return
	 */
	protected AdminUserDbVo getUser(){
		log.info("get user info");
		return ThreadData.ADMIN_USER.get();
	}
	
	
}
