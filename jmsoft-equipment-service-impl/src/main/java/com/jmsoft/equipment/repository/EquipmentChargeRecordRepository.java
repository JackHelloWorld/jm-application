package com.jmsoft.equipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.equipment.entity.EquipmentChargeRecord;

@Repository
public interface EquipmentChargeRecordRepository extends JpaRepository<EquipmentChargeRecord, Long>{
	
}
