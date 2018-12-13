package com.jmsoft.user.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.AdminRole;
import com.jmsoft.user.entity.AdminUser;
import com.jmsoft.user.vo.AdminUserVo;

@Repository
public interface ListDao {
	
	List<Map<String, Object>> roleList(AdminRole adminRole);

	List<Map<String, Object>> sysUserList(AdminUser adminUser);

	List<Map<String, Object>> webUserList(AdminUserVo adminUserVo);

	List<Map<String, Object>> findLoginLog(@Param("userId") Long userId);
	
}
