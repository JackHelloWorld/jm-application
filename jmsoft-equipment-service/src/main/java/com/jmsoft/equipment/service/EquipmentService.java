package com.jmsoft.equipment.service;

import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.equipment.vo.EquipmentVo;

/**
 * 设备服务
 * @author Jack
 *
 */
public interface EquipmentService {
	
	/***
	 * 检查用户是否存在市级代理设备
	 * @param loginUserId 检查用户
	 * @return {true:是,false:否}
	 */
	public boolean checkCityInfo(Long loginUserId);
	
	/***
	 * 检查用户是否存在县级代理设备
	 * @param loginUserId 检查用户
	 * @return {true:是,false:否}
	 */
	public boolean checkCountyInfo(Long loginUserId);
	
	/***
	 * 检查用户是否拥有设备
	 * @param loginUserId 检查用户
	 * @return {true:是,false:否}
	 */
	public boolean checkUser(Long loginUserId);
	
	/***
	 * 新增设备
	 * @param equipmentVo 设备信息
	 * @param createUserId 创建人
	 * @return 操作结果
	 */
	public ResponseResult save(EquipmentVo equipmentVo,Long createUserId) throws Exception;
	
	/***
	 * 修改设备信息
	 * @param equipmentVo 设备信息
	 * @param updateUserId 修改人
	 * @return 操作结果
	 */
	public ResponseResult update(EquipmentVo equipmentVo,Long updateUserId) throws Exception;

	/**
	 * 获取设备列表
	 * @param equipmentVo 设备查询信息
	 * @param pageNumber 页码
	 * @param pageSize 页大小
	 * @return
	 */
	public ResponseResult list(EquipmentVo equipmentVo, Integer pageNumber, Integer pageSize) throws Exception;
	
}
