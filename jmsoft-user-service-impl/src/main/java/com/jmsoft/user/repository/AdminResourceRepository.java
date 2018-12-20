package com.jmsoft.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.user.entity.AdminResource;

@Repository
public interface AdminResourceRepository extends JpaRepository<AdminResource, Long>{

	List<AdminResource> findByTypeInOrderBySortAsc(Integer[] types);

	List<AdminResource> findByParentIdAndTypeInOrderBySortAsc(Long parentId, Integer[] types);

	long countById(Long parentId);

	AdminResource findTop1ById(Long id);

}
