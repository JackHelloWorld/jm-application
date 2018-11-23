package com.jm.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jm.sys.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	List<Address> findByParentCode(String parentCode);
	
}
