package com.jmsoft.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long>{

	LoginUser findTop1ByIdAndStatusIn(Long id, Integer[] status);
	
}
