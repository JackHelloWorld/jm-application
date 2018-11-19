package com.jm.user.service.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.exception.BizException;
import com.jm.user.entity.AdminResource;
import com.jm.user.entity.AdminRole;
import com.jm.user.entity.AdminUser;
import com.jm.user.mybatis.AuthDao;
import com.jm.user.repository.AdminResourceRepository;
import com.jm.user.repository.AdminRoleRepository;
import com.jm.user.repository.AdminUserRepository;

public class BaseUserService implements Serializable{

	private static final long serialVersionUID = 4658277064179367298L;
	
	@Resource
	private AdminUserRepository adminUserRepository;
	
	@Resource
	private AdminRoleRepository adminRoleRepository;
	
	@Resource
	private AdminResourceRepository adminResourceRepository;

	@Resource
	private AuthDao authDao;

	/**
	 * 检查操作员信息
	 * @param id 当前请求操作员id
	 * @return 操作员对象
	 * @throws BizException 
	 */
	protected AdminUser checkActionUserStatus(Long id) throws BizException {
		
		if(id == null)
			ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "操作员信息不存在").throwBizException();
		
		AdminUser adminUser = adminUserRepository.findTop1ById(id);
		
		if(adminUser == null)
			ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "操作员信息不存在").throwBizException();
		
		/**账号状态,0:正常,1:停用,2:已经删除*/
		switch (adminUser.getStatus()) {
		case 1:
			ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前信息已停用,无法操作").throwBizException();
		case 2:
			ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前不存在,无法操作").throwBizException();

		default:
			break;
		}
		
		//检查角色
		if(adminUser.getIsAdmin() == null || !adminUser.getIsAdmin()){
			AdminRole adminRole = adminRoleRepository.findTop1ByIdAndStatusIn(adminUser.getRoleId(), new Integer[]{0});
			if(adminRole == null)
				ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前登录角色有误,无法操作").throwBizException();
			adminUser.setAdminRole(adminRole);
		}
		
		return adminUser;
	}
	
	/**
	 * 检查操作员信息
	 * @param id 当前请求操作员id
	 * @return 操作员对象
	 * @throws BizException 
	 */
	protected void checkActionUserStatus(AdminUser adminUser) throws BizException {
		
		if(adminUser == null)
			ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "操作员信息不存在").throwBizException();
		
		/**账号状态,0:正常,1:停用,2:已经删除*/
		switch (adminUser.getStatus()) {
		case 1:
			ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前信息已停用,无法操作").throwBizException();
		case 2:
			ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前不存在,无法操作").throwBizException();
			
		default:
			break;
		}
		
		//检查角色
		if(adminUser.getIsAdmin() == null || !adminUser.getIsAdmin()){
			AdminRole adminRole = adminRoleRepository.findTop1ByIdAndStatusIn(adminUser.getRoleId(), new Integer[]{0});
			if(adminRole == null)
				ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前登录角色有误,无法操作").throwBizException();
			adminUser.setAdminRole(adminRole);
		}
		
	}
	
	/**
	 * 处理资源信息
	 * @param sysResources
	 * @param parentId
	 * @return
	 */
	protected List<AdminResource> initResource(List<AdminResource> adminResources,Long parentId){
		List<AdminResource> nodes = new ArrayList<>();
		for (AdminResource adminResource : adminResources) {
			if(adminResource.getParentId().equals(parentId)){
				nodes.add(adminResource);
				List<AdminResource> myNodes = initResource(adminResources,adminResource.getId());
				adminResource.setNodes(myNodes);
			}
		}
		return nodes;
	}
	
	/**
	 * 获取用户资源
	 * @param userId 用户id
	 * @param types 类型
	 * @param dispose 是否需要递归处理
	 * @return
	 * @throws BizException 
	 */
	public List<AdminResource> findUserResource(Long userId,Integer[] types,boolean dispose) throws BizException {

		AdminUser adminUser = checkActionUserStatus(userId);

		List<AdminResource> list;

		if(adminUser.getIsAdmin() != null && adminUser.getIsAdmin()){
			list = adminResourceRepository.findByTypeInOrderBySortAsc(types);
		}else{
			Map<String, Object> param = new HashMap<>();
			param.put("type", types);
			param.put("roleId", adminUser.getRoleId());
			list = authDao.findThisResourceByType(param);
		}
		if(dispose)
			return  initResource(list, 0L);
		return list;
	}
	
	/**
	 * 获取用户资源
	 * @param adminUser 用户信息
	 * @param types 类型
	 * @param dispose 是否需要递归处理
	 * @return
	 * @throws BizException 
	 */
	public List<AdminResource> findUserResource(AdminUser adminUser,Integer[] types,boolean dispose) throws BizException {
		
		List<AdminResource> list;
		
		if(adminUser.getIsAdmin() != null && adminUser.getIsAdmin()){
			list = adminResourceRepository.findByTypeInOrderBySortAsc(types);
		}else{
			Map<String, Object> param = new HashMap<>();
			param.put("type", types);
			param.put("roleId", adminUser.getRoleId());
			list = authDao.findThisResourceByType(param);
		}
		if(dispose)
			return  initResource(list, 0L);
		return list;
	}
	
}
