package com.jmsoft.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.sys.entity.SerialNumber;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, Long>{

	public Long countByColumnName(String columnName);
	
}
