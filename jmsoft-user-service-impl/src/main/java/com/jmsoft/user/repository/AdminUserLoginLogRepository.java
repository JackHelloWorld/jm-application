package com.jmsoft.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.AdminUserLoginLog;

@Repository
public interface AdminUserLoginLogRepository extends JpaRepository<AdminUserLoginLog, Long>{

	long countByUserId(Long userId);
	
}
