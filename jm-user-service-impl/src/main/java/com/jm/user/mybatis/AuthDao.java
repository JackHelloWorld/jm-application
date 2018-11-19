package com.jm.user.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jm.user.entity.AdminResource;


public interface AuthDao {
	
	long countByRoleIdAndUrl(@Param("roleId")Long roleId,@Param("url")String url);

	List<Map<String, Object>> findByRoleIdAndType(@Param("roleId")Long roleId, @Param("type")Integer type,@Param("parentId")Long parentId);

	long countByUrl(@Param("url")String url,@Param("roleId") Long roleId);

	/**
	 * 获取当前用户资源信息,非管理员 
	 * @param param
	 * @return
	 */
	List<AdminResource> findThisResourceByType(Map<String, Object> param);
}
