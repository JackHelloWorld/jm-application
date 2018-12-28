package com.jmsoft.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long>{

	@Modifying
	@Query("update LoginUser set userType = :newUserType where id = :id and userType = :oldUserType and status = 0")
	int updateUserType(@Param("id")Long id, @Param("oldUserType")Integer oldUserType, @Param("newUserType")Integer newUserType);
	
}
