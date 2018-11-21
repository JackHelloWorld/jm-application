package com.jm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.jm.user.entity.AdminResourceUser;

@Repository
public interface AdminResourceUserRepository extends JpaRepository<AdminResourceUser, Long>{

	@Modifying
	void deleteByUserId(Long id);
	
}
