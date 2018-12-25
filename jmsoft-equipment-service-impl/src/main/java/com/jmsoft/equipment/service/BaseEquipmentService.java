package com.jmsoft.equipment.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.jmsoft.common.utils.BeanTools;
import com.jmsoft.equipment.entity.EquipmentOperateRecord;
import com.jmsoft.equipment.entity.EquipmentRecordInfo;
import com.jmsoft.equipment.repository.EquipmentOperateRecordRepository;
import com.jmsoft.equipment.repository.EquipmentRecordInfoRepository;
import com.jmsoft.equipment.vo.EquipmentVo;

public class BaseEquipmentService {

	@Resource
	private EquipmentRecordInfoRepository equipmentRecordInfoRepository;
	
	@Resource
	private EquipmentOperateRecordRepository equipmentOperateRecordRepository;
	
	/**
	 * 生成操作记录
	 * @param oldEquipmentVo 操作前信息
	 * @param newEquipmentVo 操作后信息
	 * @param userType 操作人类型{0:后台用户,1:前台用户}
	 * @param userId 操作人id
	 * @param equipmentId 设备id
	 */
	@Transactional(rollbackFor=Exception.class)
	public void generateRecord(EquipmentVo oldEquipmentVo,EquipmentVo newEquipmentVo,Integer userType,Long userId,Long equipmentId) {
		
		EquipmentRecordInfo equipmentRecordInfoOld = BeanTools.setPropertiesToBean(oldEquipmentVo, EquipmentRecordInfo.class);
		equipmentRecordInfoOld.setId(null);
		
		EquipmentRecordInfo equipmentRecordInfoNew = BeanTools.setPropertiesToBean(newEquipmentVo, EquipmentRecordInfo.class);
		equipmentRecordInfoNew.setId(null);
		
		equipmentRecordInfoRepository.save(equipmentRecordInfoOld);
		equipmentRecordInfoRepository.save(equipmentRecordInfoNew);
		
		EquipmentOperateRecord equipmentOperateRecord = new EquipmentOperateRecord(null, new Date(), userId, userType, 0, equipmentId, equipmentRecordInfoOld.getId(), equipmentRecordInfoNew.getId());
		equipmentOperateRecordRepository.save(equipmentOperateRecord);
		
	}
	
}
