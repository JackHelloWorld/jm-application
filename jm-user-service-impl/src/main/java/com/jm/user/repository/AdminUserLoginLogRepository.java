package com.jm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jm.user.entity.AdminUserLoginLog;

@Repository
public interface AdminUserLoginLogRepository extends JpaRepository<AdminUserLoginLog, Long>{
	
}
