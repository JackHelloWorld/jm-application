package com.jmsoft.equipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.equipment.entity.EquipmentOperateRecord;

@Repository
public interface EquipmentOperateRecordRepository extends JpaRepository<EquipmentOperateRecord, Long>{
	
}
