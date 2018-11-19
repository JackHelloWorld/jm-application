package com.jm.user.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jm.user.entity.AdminRole;
import com.jm.user.entity.AdminUser;
import com.jm.user.vo.AdminUserVo;

@Repository
public interface ListDao {
	
	List<Map<String, Object>> roleList(AdminRole adminRole);

	List<Map<String, Object>> sysUserList(AdminUser adminUser);

	List<Map<String, Object>> webUserList(AdminUserVo adminUserVo);
	
}
