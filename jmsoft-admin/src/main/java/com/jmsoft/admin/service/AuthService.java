package com.jmsoft.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jmsoft.user.vo.AdminResourceVo;
import com.jmsoft.user.vo.AdminUserDbVo;


@Service
public class AuthService {

	/**
	 * 检查资源权限信息
	 * @param adminUser
	 * @param url
	 * @return
	 */
	public boolean validate(AdminUserDbVo adminUser,String url) {
		
		if(adminUser.getIsAdmin() != null && adminUser.getIsAdmin()) 
			return true;
		
		List<AdminResourceVo> adminResources = adminUser.getAdminResources();
		
		if(adminResources != null){
			for (AdminResourceVo adminResource : adminResources) {
				if(url.trim().equals(adminResource.getUrl()))
					return true;
			}
		}
		
		return false;
	}
	
}
