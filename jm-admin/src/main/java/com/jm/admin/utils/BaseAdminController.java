package com.jm.admin.utils;

import com.jm.sys.web.BaseController;
import com.jm.user.entity.AdminUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseAdminController extends BaseController{

	private static final long serialVersionUID = 2708622649554257953L;

	/**
	 * 获取登录用户
	 * @return
	 */
	protected AdminUser getUser(){
		log.info("get user info");
		return ThreadData.ADMIN_USER.get();
	}
	
	
}
