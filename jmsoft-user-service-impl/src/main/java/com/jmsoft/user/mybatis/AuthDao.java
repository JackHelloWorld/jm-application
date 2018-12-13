package com.jmsoft.user.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jmsoft.user.vo.AdminResourceVo;


public interface AuthDao {
	
	long countByRoleIdAndUrl(@Param("roleId")Long roleId,@Param("url")String url);

	List<Map<String, Object>> findByRoleIdAndType(@Param("roleId")Long roleId, @Param("type")Integer type,@Param("parentId")Long parentId);

	long countByUrl(@Param("url")String url,@Param("roleId") Long roleId);

	/**
	 * 获取当前用户资源信息,非管理员 
	 * @param param {roleId:角色id,parentId:父id,type:类型集合}
	 * @return
	 */
	List<AdminResourceVo> findRoleResourceByType(Map<String, Object> param);

	/**
	 * 获取用户资源信息
	 * @param param {roleId:角色id,parentId:父id,type:类型集合,userId : 用户id}
	 * @return
	 */
	List<AdminResourceVo> findThisResourceByType(Map<String, Object> param);
}
