package com.jmsoft.wxservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.wxservice.entity.AccessToken;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long>{

	AccessToken findTop1ByExpiresInNotNullOrderByCreateTimeDesc();
	
}
