package com.jmsoft.equipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.equipment.entity.EquipmentRecordInfo;

@Repository
public interface EquipmentRecordInfoRepository extends JpaRepository<EquipmentRecordInfo, Long>{
	
}
