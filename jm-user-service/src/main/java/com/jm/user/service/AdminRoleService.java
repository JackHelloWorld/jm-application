package com.jm.user.service;

import java.util.List;

import com.jm.common.data.ResponseResult;
import com.jm.common.exception.BizException;
import com.jm.user.vo.AdminRoleVo;
import com.jm.user.vo.AdminUserDbVo;

/**
 * 角色模块
 * @author Jack
 *
 */
public interface AdminRoleService {

	/***
	 * 获取所以角色信息
	 * @return
	 */
	public List<AdminRoleVo> findAllRoles();

	/**
	 * 获得角色信息列表
	 * @param pageNumber
	 * @param pageSize
	 * @param adminRole
	 * @return
	 */
	public ResponseResult list(Integer pageNumber, Integer pageSize, AdminRoleVo adminRole) throws Exception;

	/**
	 * 获取角色信息
	 * @param id
	 * @return
	 */
	public ResponseResult updateInfo(Long id) throws Exception;

	/**
	 * 保存角色信息
	 * @param adminRole 角色信息
	 * @param user 操作人信息
	 * @return
	 */
	public ResponseResult save(AdminRoleVo adminRole, AdminUserDbVo user) throws Exception;

	/**
	 * 删除角色信息
	 * @param adminRole 角色信息
	 * @param user 操作人信息
	 * @return
	 */
	public ResponseResult delete(Long id, AdminUserDbVo user) throws Exception;

	/**
	 * 修改角色信息
	 * @param adminRole 角色信息
	 * @param user 操作人信息
	 * @return
	 */
	public ResponseResult update(AdminRoleVo adminRole, AdminUserDbVo user) throws Exception;

	/**
	 * 根据角色id获取角色资源
	 * @param id 角色id
	 * @param user 操作人信息
	 * @return
	 */
	public ResponseResult findResource(Long id, AdminUserDbVo user) throws Exception;

	/**
	 * 角色资源授权
	 * @param id 角色id 
	 * @param ids 资源信息
	 * @param user 操作人信息
	 * @return
	 */
	public ResponseResult resource(Long id, String[] ids, AdminUserDbVo user) throws Exception;

	/**
	 * 启用角色信息
	 * @param id 角色信息id
	 * @param user 操作员信息
	 * @return
	 * @throws Exception
	 */
	public ResponseResult success(Long id, AdminUserDbVo user) throws Exception;

	/**
	 * 禁用角色信息
	 * @param id 角色信息id
	 * @param user 操作员信息
	 * @return
	 * @throws Exception
	 */
	public ResponseResult block(Long id, AdminUserDbVo user) throws Exception;

	/**
	 * 用户资源授权
	 * @param userId 用户id 
	 * @param ids 资源信息
	 * @param user 操作人信息
	 * @return
	 */
	public ResponseResult resourceUser(Long userId, String[] ids, AdminUserDbVo user)  throws BizException;
	
}
