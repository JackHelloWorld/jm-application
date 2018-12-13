package com.jmsoft.admin.utils;

import com.jmsoft.user.vo.AdminUserDbVo;

public final class ThreadData {

	/**系统登录用户*/
	public static final ThreadLocal<AdminUserDbVo> ADMIN_USER = new ThreadLocal<AdminUserDbVo>();
	
}
