package com.jmsoft.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.AdminUserStatusRecord;

@Repository
public interface AdminUserStatusRecordRepository extends JpaRepository<AdminUserStatusRecord, Long>{
	
}
