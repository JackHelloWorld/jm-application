package com.jmsoft.equipment.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jmsoft.equipment.entity.EquipmentChargeRecord;

@Repository
public interface EquipmentChargeRecordRepository extends JpaRepository<EquipmentChargeRecord, Long>{

	@Modifying
	@Query(nativeQuery=true,value="UPDATE biz_equipment_charge_record SET e_status = 2,e_reset_time = :resetTime WHERE (e_equipment_id=:equipmentId and e_status = 1)")
	void resetInfo(@Param("equipmentId")Long equipmentId, @Param("resetTime")Date resetTime);
	
}
