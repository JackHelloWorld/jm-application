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

	/**
	 * 删除设备
	 * @param id 设备id
	 * @param userId 操作人
	 * @return
	 */
	public ResponseResult delete(Long id, Long userId);

	/**
	 * 启用设备
	 * @param id 设备id
	 * @param userId 操作人
	 * @return
	 */
	public ResponseResult success(Long id, Long userId);

	/**
	 * 停用设备
	 * @param id 设备id
	 * @param userId 操作人
	 * @return
	 */
	public ResponseResult block(Long id, Long userId);

	/**
	 * 重置设备
	 * @param id 设备id
	 * @param userId 操作人
	 * @return
	 */
	public ResponseResult reset(Long id, Long userId);

	/**
	 * 商家扫码绑定设备
	 * @param equipmentNo 设备编码
	 * @param userId 操作人
	 * @return
	 */
	public ResponseResult scanBind(String equipmentNo, Long userId) throws Exception;
	
}
