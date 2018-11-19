package com.jm.user.service;

import java.util.List;

import com.jm.user.entity.AdminRole;

/**
 * 角色模块
 * @author Jack
 *
 */
public interface AdminRoleService {

	public List<AdminRole> findAllRoles();
	
}
