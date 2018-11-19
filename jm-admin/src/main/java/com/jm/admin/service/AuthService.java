package com.jm.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jm.user.entity.AdminResource;
import com.jm.user.entity.AdminUser;

@Service
public class AuthService {

	/**
	 * 检查资源权限信息
	 * @param adminUser
	 * @param url
	 * @return
	 */
	public boolean validate(AdminUser adminUser,String url) {
		
		if(adminUser.getIsAdmin() != null && adminUser.getIsAdmin()) 
			return true;
		
		List<AdminResource> adminResources = adminUser.getAdminResources();
		
		if(adminResources != null){
			for (AdminResource adminResource : adminResources) {
				if(url.trim().equals(adminResource.getUrl()))
					return true;
			}
		}
		
		return false;
	}
	
}
