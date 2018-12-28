package com.jmsoft.equipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.equipment.entity.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>{

	long countByCityUserIdAndStatusIn(Long loginUserId, Integer[] status);

	long countByCountyUserIdAndStatusIn(Long loginUserId, Integer[] status);

	long countByLoginUserIdAndStatusIn(Long loginUserId, Integer[] status);

	long countByNoAndStatusIn(String no, Integer[] status);

	int countByNoAndStatusInAndIdNot(String no, Integer[] status, Long id);

	Equipment findTop1ByIdAndStatusIn(Long id, Integer[] status);

	Equipment findTop1ByNoAndStatusIn(String no, Integer[] status);
	
}
