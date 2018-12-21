package com.jmsoft.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.LoginUserStatusRecord;

@Repository
public interface LoginUserStatusRecordRepository extends JpaRepository<LoginUserStatusRecord, Long>{
	
}
