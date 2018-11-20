package com.jm.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jm.user.entity.AdminRole;

@Repository
public interface AdminRoleRepository extends JpaRepository<AdminRole, Long>{

	AdminRole findTop1ByIdAndStatusIn(Long roleId, Integer[] status);

	List<AdminRole> findByStatusOrderByCreateTimeDesc(Integer status);
	
	long countByIdAndStatus(Long roleId, Integer status);

	long countByNameAndStatusNot(String name, Integer status);

	long countByNameAndStatusNotAndIdNot(String name, Integer status, Long id);

	AdminRole findTop1ByIdAndStatusNot(Long id, Integer status);
	
	
}
