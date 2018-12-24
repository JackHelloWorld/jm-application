package com.jmsoft.user.service;

import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.user.vo.AdminUserDbVo;
import com.jmsoft.user.vo.LoginUserVo;

/**
 * 前端用户服务
 * @author Jack
 *
 */
public interface LoginUserService {

	/**
	 * 获取登录用户列表
	 * @param loginUserVo 查询信息
	 * @param pageNumber 页码
	 * @param pageSize 页大小
	 * @return 登录用户信息
	 */
	public ResponseResult list(LoginUserVo loginUserVo,Integer pageNumber,Integer pageSize) throws Exception;

	/***
	 * 资料修改
	 * @param loginUserVo 用户资料
	 * @param updateType 是否允许修改类型
	 * @return
	 * @throws Exception
	 */
	public ResponseResult updateInfo(LoginUserVo loginUserVo,boolean updateType) throws Exception;

	/**
	 * 删除用户
	 * @param id 用户id
	 * @param user 操作人
	 * @return
	 */
	public ResponseResult delete(Long id, AdminUserDbVo user);

	/**
	 * 启用用户
	 * @param id 用户id
	 * @param user 操作人
	 * @return
	 */
	public ResponseResult success(Long id, AdminUserDbVo user);

	/**
	 * 停用用户
	 * @param id 用户id
	 * @param user 操作人
	 * @return
	 */
	public ResponseResult block(Long id, AdminUserDbVo user);

	/**
	 * 保存前端用户信息
	 * @param loginUserVo
	 * @return
	 */
	public ResponseResult save(LoginUserVo loginUserVo) throws Exception;

	/**
	 * 判断用户是否为市级代理人
	 * @param userId 用户id
	 * @return
	 */
	public boolean isCityUser(Long userId);

	/**
	 * 判断用户是否为县级代理人
	 * @param userId 用户id
	 * @return
	 */
	public boolean isCountyUser(Long userId);
}
