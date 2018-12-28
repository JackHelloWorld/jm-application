package com.jmsoft.equipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	Equipment findTop1ByScanQrCodeAndStatusIn(String scanQrCode, Integer[] status);

	Equipment findTop1ById(Long id);

	@Modifying
	@Query("update Equipment set status = :newStatus where id = :id and status = :oldStatus")
	int updateStatus(@Param("id")Long id, @Param("oldStatus")Integer oldStatus, @Param("newStatus")Integer newStatus);
	
}
