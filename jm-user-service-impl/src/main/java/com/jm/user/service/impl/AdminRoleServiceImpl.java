package com.jm.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.jm.user.entity.AdminRole;
import com.jm.user.repository.AdminRoleRepository;
import com.jm.user.service.AdminRoleService;
import com.jm.user.service.utils.BaseUserService;

@Service
public class AdminRoleServiceImpl  extends BaseUserService implements AdminRoleService{

	private static final long serialVersionUID = 7932281858437022915L;
	
	@Resource
	private AdminRoleRepository adminRoleRepository;

	@Override
	public List<AdminRole> findAllRoles() {
		return adminRoleRepository.findByStatusOrderByCreateTimeDesc(0);
	}

}
