package com.jm.user.service;

import com.jm.common.data.ResponseResult;
import com.jm.common.exception.BizException;
import com.jm.common.utils.PageBean;
import com.jm.user.vo.AdminUserDbVo;
import com.jm.user.vo.AdminUserLoginVo;
import com.jm.user.vo.AdminUserProfileVo;
import com.jm.user.vo.AdminUserVo;
import com.jm.user.vo.UpdatePasswordVo;

/**
 * 用户模块
 * @author Jack
 *
 */
public interface UserService {

	/**
	 * 保存后台用户数据
	 * @param adminUserVo 保存信息
	 * @return
	 */
	public ResponseResult save(AdminUserVo adminUserVo)  throws Exception ;
	
	/**
	 * 删除后台用户数据
	 * @param 用户id
	 * @return
	 */
	public ResponseResult delete(Long id,Long actionUserId)  throws Exception ;
	
	/**
	 * 修改后台用户数据
	 * @param adminUserVo 修改信息
	 * @return
	 * @throws RuntimeException
	 */
	public ResponseResult update(AdminUserVo adminUserVo)  throws Exception ;
	
	/**
	 * 启用用户
	 * @param 用户id 
	 * @return
	 */
	public ResponseResult using(Long id,Long actionUserId)  throws Exception ;
	
	/**
	 * 停用用户
	 * @param 用户id 
	 * @return
	 */
	public ResponseResult disabled(Long id,Long actionUserId)  throws Exception ;

	/**
	 * 用户登录
	 * @param adminUserLoginVo
	 * @return
	 */
	public ResponseResult login(AdminUserLoginVo adminUserLoginVo) throws Exception;

	/**
	 * 查询后台用户信息
	 * @param pageNumber 页码
	 * @param pageSize 页大小
	 * @param adminUserVo 查询信息
	 * @return
	 * @throws Exception
	 */
	public PageBean webUserList(Integer pageNumber,Integer pageSize,AdminUserVo adminUserVo)  throws Exception;

	/**
	 * 根据用户id获取用户信息
	 * @param id 用户id
	 * @return 用户信息
	 */
	public AdminUserDbVo findInfoById(Long id) throws BizException;

	/**
	 * 获取指定用户的资源信息
	 * @param id 用户id
	 * @param user 操作员信息
	 * @return
	 */
	public ResponseResult findUserResource(Long id, AdminUserDbVo user) throws BizException;

	/**
	 * 获取指定用户的登录次数
	 * @param adminUser 用户信息
	 * @return
	 */
	public ResponseResult countLogininfo(AdminUserDbVo adminUser);

	/**
	 * 获取指定用户的登录记录
	 * @param pageNumber 页码
	 * @param pageSize 页大小
	 * @param adminUser 用户信息
	 * @return
	 */
	public PageBean findLogininfo(Integer pageNumber, Integer pageSize, AdminUserDbVo user) throws Exception;

	/**
	 * 修改用户资料
	 * @param adminUser 修改信息
	 * @return
	 */
	public ResponseResult updateInfo(AdminUserProfileVo adminUserProfileVo) throws Exception;

	/**
	 * 修改用户密码
	 * @param updatePasswordVo 密码信息
	 * @return
	 */
	public ResponseResult updatePassword(UpdatePasswordVo updatePasswordVo) throws Exception;

	/**
	 * 用户修改头像
	 * @param profile
	 * @param user
	 * @return
	 */
	public ResponseResult updateProfile(String profile, AdminUserDbVo user) throws BizException;

	
}
