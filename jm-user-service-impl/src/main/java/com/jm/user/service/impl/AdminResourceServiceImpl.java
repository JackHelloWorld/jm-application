package com.jm.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.jm.sys.exception.BizException;
import com.jm.user.entity.AdminResource;
import com.jm.user.entity.AdminUser;
import com.jm.user.mybatis.AuthDao;
import com.jm.user.repository.AdminResourceRepository;
import com.jm.user.service.AdminResourceService;
import com.jm.user.service.utils.BaseUserService;

@Service(retries=0,timeout=5000)
@Transactional(rollbackFor=Exception.class)
public class AdminResourceServiceImpl extends BaseUserService implements AdminResourceService  { 

	private static final long serialVersionUID = -8514405936502690688L;

	@Resource
	private AdminResourceRepository adminResourceRepository;

	@Resource
	private AuthDao authDao;

	@Override
	public List<AdminResource> findUserResource(Long userId, Integer[] types, Long parentId, boolean dispose)
			throws BizException {

		AdminUser adminUser = checkActionUserStatus(userId);

		List<AdminResource> list;

		if(adminUser.getIsAdmin() != null && adminUser.getIsAdmin()){
			list = adminResourceRepository.findByParentIdAndTypeInOrderBySortAsc(parentId,types);
		}else{
			Map<String, Object> param = new HashMap<>();
			param.put("type", types);
			param.put("roleId", adminUser.getRoleId());
			param.put("parentId", parentId);
			list = authDao.findThisResourceByType(param);
		}
		if(dispose)
			return  initResource(list, 0L);
		return list;
	
	}

}
