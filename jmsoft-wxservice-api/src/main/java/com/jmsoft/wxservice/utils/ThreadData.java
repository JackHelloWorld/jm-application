package com.jmsoft.wxservice.utils;

import com.jmsoft.user.vo.LoginUserVo;

public final class ThreadData {

	/**登录用户*/
	public static final ThreadLocal<LoginUserVo> LOGIN_USER = new ThreadLocal<LoginUserVo>();
	
}
