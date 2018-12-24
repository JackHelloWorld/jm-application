package com.jmsoft.equipment.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jmsoft.equipment.vo.EquipmentVo;

@Repository
public interface EquipmentDao {

	public List<Map<String, Object>> pageList(EquipmentVo equipmentVo);

}
