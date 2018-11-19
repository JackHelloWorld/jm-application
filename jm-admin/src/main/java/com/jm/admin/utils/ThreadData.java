package com.jm.admin.utils;

import com.jm.user.entity.AdminUser;

public final class ThreadData {

	/**系统登录用户*/
	public static final ThreadLocal<AdminUser> ADMIN_USER = new ThreadLocal<AdminUser>();
	
}
