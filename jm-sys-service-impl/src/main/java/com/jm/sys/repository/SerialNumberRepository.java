package com.jm.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jm.sys.entity.SerialNumber;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, Long>{

	public Long countByColumnName(String columnName);
	
}
