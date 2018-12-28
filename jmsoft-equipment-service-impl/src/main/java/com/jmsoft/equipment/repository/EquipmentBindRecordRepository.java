package com.jmsoft.equipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.equipment.entity.EquipmentBindRecord;

@Repository
public interface EquipmentBindRecordRepository extends JpaRepository<EquipmentBindRecord, Long>{
	
}
