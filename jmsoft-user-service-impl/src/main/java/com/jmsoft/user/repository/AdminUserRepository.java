package com.jmsoft.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.AdminUser;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long>{

	AdminUser findTop1ById(Long id);

	long countByLoginNameAndStatusIn(String loginName, Integer[] status);

	long countByLoginNameAndStatusInAndIdNot(String loginName, Integer[] status, Long id);

	AdminUser findTop1ByLoginNameAndStatusIn(String loginName, Integer[] status);

	AdminUser findTop1ByIdAndStatusNot(Long id, Integer status);

	long countByRoleIdAndStatusNot(Long id, Integer status);
	
}
