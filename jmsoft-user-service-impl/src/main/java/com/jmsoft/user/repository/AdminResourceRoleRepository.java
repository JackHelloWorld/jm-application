package com.jmsoft.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.AdminResourceRole;

@Repository
public interface AdminResourceRoleRepository extends JpaRepository<AdminResourceRole, Long>{

	@Modifying
	void deleteByRoleId(Long roleId);
	
}
