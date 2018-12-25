package com.jmsoft.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long>{

	LoginUser findTop1ByIdAndStatusIn(Long id, Integer[] status);

	long countByIdAndStatusAndUserType(Long userId, Integer status, Integer userType);

	List<LoginUser> findByStatusAndUserTypeInOrderByUpdateTimeDesc(Integer status, Integer[] type);
	
}
