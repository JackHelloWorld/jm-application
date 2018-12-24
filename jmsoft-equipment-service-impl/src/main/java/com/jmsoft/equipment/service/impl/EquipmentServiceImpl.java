package com.jmsoft.equipment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.exception.ParamException;
import com.jmsoft.common.utils.AnnotationUtils;
import com.jmsoft.common.utils.BeanTools;
import com.jmsoft.common.utils.PageBean;
import com.jmsoft.common.utils.PageQuery;
import com.jmsoft.common.utils.PageUtils;
import com.jmsoft.equipment.entity.Equipment;
import com.jmsoft.equipment.mybatis.EquipmentDao;
import com.jmsoft.equipment.repository.EquipmentRepository;
import com.jmsoft.equipment.service.EquipmentService;
import com.jmsoft.equipment.vo.EquipmentVo;
import com.jmsoft.user.service.LoginUserService;
import com.jmsoft.user.service.UserService;

@Service
@Transactional(rollbackFor=Exception.class)
public class EquipmentServiceImpl implements EquipmentService{

	@Resource
	EquipmentRepository equipmentRepository;
	
	@Resource
	EquipmentDao equipmentDao;

	@Reference
	LoginUserService loginUserService;

	@Reference
	UserService userService;

	@Override
	public boolean checkCityInfo(Long loginUserId) {
		long count = equipmentRepository.countByCityUserIdAndStatusIn(loginUserId,new Integer[]{0,1,2,3});
		return count > 0;
	}

	@Override
	public boolean checkCountyInfo(Long loginUserId) {
		long count = equipmentRepository.countByCountyUserIdAndStatusIn(loginUserId,new Integer[]{0,1,2,3});
		return count > 0;
	}

	@Override
	public boolean checkUser(Long loginUserId) {
		long count = equipmentRepository.countByLoginUserIdAndStatusIn(loginUserId,new Integer[]{0,1,2,3});
		return count > 0;
	}

	@Override
	public ResponseResult save(EquipmentVo equipmentVo,Long createUserId) throws Exception {

		userService.checkUserStatus(createUserId); 

		AnnotationUtils.validateEdit(equipmentVo);

		Equipment equipment = BeanTools.setPropertiesToBean(equipmentVo, Equipment.class);

		//init data
		equipment.setStatus(0);
		equipment.setId(null);
		equipment.setCreateTime(new Date());
		equipment.setNo(equipment.getNo().trim());
		equipment.setCreateUserId(createUserId);
		equipment.setLoginUserId(null);
		equipment.setScanQrCode(null);

		validateInfo(equipment);
		equipmentRepository.save(equipment);
		return ResponseResult.SUCCESSM("保存信息成功");
	}

	/**
	 * 验证参数	
	 * @param equipment
	 * @throws ParamException
	 */
	private void validateInfo(Equipment equipment) throws ParamException {

		if(equipment.getId() == null) {
			if(equipmentRepository.countByNoAndStatusIn(equipment.getNo(),new Integer[]{0,1,2,3,4}) > 0) 
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备编号不能重复").throwParamException();
		}else {
			if(equipmentRepository.countByNoAndStatusInAndIdNot(equipment.getNo(),new Integer[]{0,1,2,3,4},equipment.getId()) > 0) 
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备编号不能重复").throwParamException();
		}
		
		if(equipment.getCityMoney() != null && equipment.getCityMoney().compareTo(BigDecimal.ZERO) == 1 || equipment.getCityUserId() != null) {

			if(equipment.getCityMoney() == null || equipment.getCityMoney().compareTo(BigDecimal.ZERO) == -1) 
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "市级代理金额输入错误").throwParamException();

			if(equipment.getCityUserId() == null) 
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "未选择市级代理人").throwParamException();

			if(! loginUserService.isCityUser(equipment.getCityUserId())) {
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "市级代理人选择错误").throwParamException();
			}

		}else {
			equipment.setCityMoney(BigDecimal.ZERO);
		}

		if(equipment.getCountyMoney() != null && equipment.getCountyMoney().compareTo(BigDecimal.ZERO) == 1 || equipment.getCountyUserId() != null) {

			if(equipment.getCountyMoney() == null || equipment.getCountyMoney().compareTo(BigDecimal.ZERO) == -1) 
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "县级代理金额输入错误").throwParamException();

			if(equipment.getCountyUserId() == null) 
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "未选择县级代理人").throwParamException();

			if(! loginUserService.isCountyUser(equipment.getCountyUserId())) {
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "县级代理人选择错误").throwParamException();
			}

		}else {
			equipment.setCountyMoney(BigDecimal.ZERO);
		}

		if(equipment.getTerraceMoney() == null || equipment.getTerraceMoney().compareTo(BigDecimal.ZERO) == -1)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "平台金额输入错误").throwParamException();

		if(equipment.getPrice() != null && equipment.getPrice().compareTo(BigDecimal.ZERO) != 1)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "单价输入错误").throwParamException();

		if(equipment.getMaxElectric() == null || equipment.getMaxElectric().compareTo(BigDecimal.ZERO) != 1)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "单次最大电量输入错误").throwParamException();

		if(equipment.getUnderMoney() == null || equipment.getUnderMoney().compareTo(BigDecimal.ZERO) == -1)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "低于余额拉闸金额输入错误").throwParamException();

		if(equipment.getLeastMoney() == null || equipment.getLeastMoney().compareTo(BigDecimal.ZERO) == -1)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "最低启动金额输入错误").throwParamException();
	}

	@Override
	public ResponseResult update(EquipmentVo equipmentVo, Long updateUserId) throws Exception {

		userService.checkUserStatus(updateUserId); 
		
		if(equipmentVo.getId() == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		
		Equipment equipmentDb = equipmentRepository.findTop1ByIdAndStatusIn(equipmentVo.getId(),new Integer[]{0,1,2,3,4});

		if(equipmentDb == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		
		if(equipmentDb.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备使用中,无法修改");
		
		AnnotationUtils.validateEdit(equipmentVo);

		Equipment equipment = BeanTools.setPropertiesToBean(equipmentVo, Equipment.class);

		//set data
		equipment.setStatus(equipmentDb.getStatus());
		equipment.setId(equipmentDb.getId());
		equipment.setCreateTime(equipmentDb.getCreateTime());
		equipment.setNo(equipment.getNo().trim());
		equipment.setCreateUserId(equipmentDb.getCreateUserId());
		equipment.setLoginUserId(equipmentDb.getLoginUserId());
		equipment.setScanQrCode(equipmentDb.getScanQrCode());

		validateInfo(equipment);
		
		equipmentRepository.save(equipment);
		return ResponseResult.SUCCESSM("保存信息成功");
	}

	@Override
	public ResponseResult list(EquipmentVo equipmentVo, Integer pageNumber, Integer pageSize) throws Exception {
		
		AnnotationUtils.paramQuery(equipmentVo);
		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {
			
			@Override
			public List<Map<String, Object>> query() {
				return equipmentDao.pageList(equipmentVo);
			}
		}, EquipmentVo.class);
		
		return ResponseResult.SUCCESS("获取设备信息成功",pageBean);
	}

}
