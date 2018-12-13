package com.jmsoft.user.service;

import java.util.List;

import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.user.vo.AdminResourceVo;

/**
 * 用户资源模块
 * @author Jack
 *
 */
public interface AdminResourceService {

	/**
	 * 获取用户资源,不用处理直接返回整个资源
	 * @param userId 用户id
	 * @param type 类型{0:菜单,1:按钮}
	 * @param dispose 是否递归处理{true:是,false:否}
	 * @return
	 */
	public List<AdminResourceVo> findUserResource(Long userId,Integer[] types,boolean dispose) throws BizException;
	
	/**
	 * 获取用户资源,不用处理直接返回整个资源
	 * @param userId 用户id
	 * @param type 类型{0:菜单,1:按钮}
	 * @param parentId 父级ID
	 * @param dispose 是否递归处理{true:是,false:否}
	 * @return
	 */
	public List<AdminResourceVo> findUserResource(Long userId,Integer[] types,Long parentId,boolean dispose) throws BizException;

	/**
	 * 获取所以资源信息
	 * @param id
	 * @return
	 */
	public List<AdminResourceVo> findAllResource(Long id) throws BizException;

	/***
	 * 删除资源信息
	 * @param id 资源id
	 * @param userId 操作员id
	 * @return
	 */
	public ResponseResult delete(String id, Long userId) throws BizException;

	/***
	 * 修改资源信息
	 * @param id 资源信息
	 * @param userId 操作员id
	 * @return
	 */
	public ResponseResult update(AdminResourceVo adminResource, Long userId) throws Exception;

	/***
	 * 保存资源信息
	 * @param id 资源信息
	 * @param userId 操作员id
	 * @return
	 */
	public ResponseResult save(AdminResourceVo adminResource, Long userId)  throws Exception;
	
}
