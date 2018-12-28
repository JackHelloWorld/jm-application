package com.jmsoft.equipment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
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
import com.jmsoft.common.utils.Tools;
import com.jmsoft.equipment.entity.Equipment;
import com.jmsoft.equipment.entity.EquipmentBindRecord;
import com.jmsoft.equipment.mybatis.EquipmentDao;
import com.jmsoft.equipment.repository.EquipmentBindRecordRepository;
import com.jmsoft.equipment.repository.EquipmentChargeRecordRepository;
import com.jmsoft.equipment.repository.EquipmentRepository;
import com.jmsoft.equipment.service.BaseEquipmentService;
import com.jmsoft.equipment.service.EquipmentService;
import com.jmsoft.equipment.vo.EquipmentVo;
import com.jmsoft.equipment.vo.UpdateEquipmentInfoVo;
import com.jmsoft.user.repository.LoginUserRepository;
import com.jmsoft.user.service.LoginUserService;
import com.jmsoft.user.service.UserService;
import com.jmsoft.user.vo.LoginUserVo;

@Service
@Transactional(rollbackFor=Exception.class)
public class EquipmentServiceImpl extends BaseEquipmentService implements EquipmentService{

	@Resource
	EquipmentRepository equipmentRepository;

	@Resource
	EquipmentChargeRecordRepository equipmentChargeRecordRepository;

	@Resource
	LoginUserRepository loginUserRepository;

	@Resource
	EquipmentDao equipmentDao;

	@Resource
	EquipmentBindRecordRepository equipmentBindRecordRepository;

	@Reference
	LoginUserService loginUserService;

	@Reference
	UserService userService;

	@Value("${user.equipment.code.rule}")
	private String equipmentCodeRule;

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

		//生成记录
		generateRecord(BeanTools.setPropertiesToBean(equipmentDb, EquipmentVo.class), 
				BeanTools.setPropertiesToBean(equipment, EquipmentVo.class) , 0, 
				updateUserId, equipment.getId());


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

	@Override
	public ResponseResult delete(Long id, Long userId) {

		Equipment equipment = equipmentRepository.findTop1ByIdAndStatusIn(id, new Integer[] {0,1,2,3,4});

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		if(equipment.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备正在使用中,无法操作");

		EquipmentVo equipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);
		equipment.setDeleteUserId(userId);
		equipment.setDeleteTime(new Date());
		equipment.setStatus(9);
		equipmentRepository.save(equipment);
		generateRecord(equipmentVo, BeanTools.setPropertiesToBean(equipment, EquipmentVo.class), 0, userId, id);

		return ResponseResult.SUCCESSM("删除设备成功");
	}

	@Override
	public ResponseResult success(Long id, Long userId) {

		Equipment equipment = equipmentRepository.findTop1ByIdAndStatusIn(id, new Integer[] {0,1,2,3,4});

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		if(equipment.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备正在使用中,无法操作");

		if(equipment.getStatus() != 4)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备为可使用状态,无需启用");

		EquipmentVo equipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);

		equipment.setStatus(1);
		equipmentRepository.save(equipment);
		generateRecord(equipmentVo, BeanTools.setPropertiesToBean(equipment, EquipmentVo.class), 0, userId, id);

		return ResponseResult.SUCCESSM("启用设备成功");
	}

	@Override
	public ResponseResult block(Long id, Long userId) {

		Equipment equipment = equipmentRepository.findTop1ByIdAndStatusIn(id, new Integer[] {0,1,2,3,4});

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		if(equipment.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备正在使用中,无法操作");

		if(equipment.getStatus() == 4)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备已停用,无需操作");

		if(equipment.getStatus() == 0) 
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备未使用,无需停用操作");

		EquipmentVo equipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);

		equipment.setStatus(4);
		equipmentRepository.save(equipment);
		generateRecord(equipmentVo, BeanTools.setPropertiesToBean(equipment, EquipmentVo.class), 0, userId, id);

		return ResponseResult.SUCCESSM("停用用设备成功");
	}

	@Override
	public ResponseResult reset(Long id, Long userId) {

		Equipment equipment = equipmentRepository.findTop1ByIdAndStatusIn(id, new Integer[] {0,1,2,3,4});

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		if(equipment.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备正在使用中,无法操作");

		EquipmentVo equipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);

		equipment.setStatus(0);
		equipment.setAddress(null);
		equipment.setCityMoney(null);
		equipment.setCityUserId(null);
		equipment.setCountyMoney(null);
		equipment.setCountyUserId(null);
		equipment.setDeleteTime(null);
		equipment.setDeleteUserId(null);
		equipment.setLatitude(null);
		equipment.setLeastMoney(null);
		equipment.setLoginUserId(null);
		equipment.setLongitude(null);
		equipment.setMaxElectric(null);
		equipment.setPrice(null);
		equipment.setScanQrCode(null);

		equipmentRepository.save(equipment);
		generateRecord(equipmentVo, BeanTools.setPropertiesToBean(equipment, EquipmentVo.class), 0, userId, id);

		//重置记录
		equipmentChargeRecordRepository.resetInfo(equipment.getId(),new Date());

		return ResponseResult.SUCCESSM("重置设备成功");
	}

	@Override
	public ResponseResult scanBind(String equipmentNo, Long userId) throws Exception {

		if(Tools.isEmpty(equipmentNo))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		LoginUserVo loginUserVo = loginUserService.findInfoById(userId);

		//检查设备
		Equipment equipment = equipmentRepository.findTop1ByNoAndStatusIn(equipmentNo.trim(), new Integer[] {0,1,2,3,4});

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		if(equipment.getStatus() != 0) {

			/**状态,{0:初始状态,1:共享中,2:使用中,3:取消共享,4:已停用,9:已删除}*/
			switch (equipment.getStatus()) {
			case 4:
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备已停用,无法绑定");

			default:

				if(!equipment.getLoginUserId().equals(userId))
					return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备已被绑定,当前操作失败");
				return ResponseResult.SUCCESS("信息绑定成功", equipment.getScanQrCode());
			}
		}

		//生成绑定信息
		EquipmentBindRecord equipmentBindRecord = new EquipmentBindRecord();
		equipmentBindRecord.setBindTime(new Date());
		equipmentBindRecord.setBindUserId(userId);
		equipmentBindRecord.setEquipmentId(equipment.getId());
		equipmentBindRecord.setStatus(0);

		EquipmentVo oldEquipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);

		//生成二维码
		String code = Tools.MD5(equipmentNo.trim().concat(loginUserVo.getWxOpenId()), "scan_code");

		String scanCode = equipmentCodeRule.replace("{code}", '{'+code+'}');

		equipmentBindRecord.setScanQrCode(scanCode);
		equipment.setScanQrCode(scanCode);
		equipment.setStatus(3);
		equipment.setLoginUserId(loginUserVo.getId());

		generateRecord(oldEquipmentVo , BeanTools.setPropertiesToBean(equipment, EquipmentVo.class), 1, userId, equipment.getId());

		equipmentRepository.save(equipment);
		equipmentBindRecordRepository.save(equipmentBindRecord);

		if(loginUserVo.getUserType() == 0) {

			int count = loginUserRepository.updateUserType(loginUserVo.getId(),0,1);
			if(count != 1)
				ResponseResult.DIY_ERROR(ResultCode.SysErrorCode, "系统繁忙,请稍后重试").throwBizException();
		}

		return ResponseResult.SUCCESS("信息绑定成功", equipment.getScanQrCode());
	}

	@Override
	public ResponseResult updateEquipmentInfo(UpdateEquipmentInfoVo updateEquipmentInfoVo, Long userId) throws Exception {

		LoginUserVo loginUserVo = loginUserService.findInfoById(userId);

		AnnotationUtils.validateEdit(updateEquipmentInfoVo);

		Equipment equipment = equipmentRepository.findTop1ById(updateEquipmentInfoVo.getId());

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备不存在");

		if(!equipment.getLoginUserId().equals(loginUserVo.getId()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "无权操作此设备");

		/**状态,{0:初始状态,1:共享中,2:使用中,3:取消共享,4:已停用,9:已删除}*/
		switch (equipment.getStatus()) {

		case 9:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		case 0:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "无权操作此设备");

		case 2:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备正在使用中,无法修改");

		case 4:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备已停用,无法修改");

		default:
			break;
		}

		EquipmentVo oldEquipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);

		equipment.setAddress(updateEquipmentInfoVo.getAddress().trim());
		equipment.setLongitude(updateEquipmentInfoVo.getLongitude());
		equipment.setLatitude(updateEquipmentInfoVo.getLatitude());

		if(equipment.getPrice().compareTo(BigDecimal.ZERO) != 1)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备单价输入错误");

		equipment.setRemark(updateEquipmentInfoVo.getRemark());

		generateRecord(oldEquipmentVo , BeanTools.setPropertiesToBean(equipment, EquipmentVo.class), 1, userId, equipment.getId());

		equipmentRepository.save(equipment);

		return ResponseResult.SUCCESSM("设备信息更新成功");
	}

	@Override
	public ResponseResult share(Long id, Long userId) throws Exception {

		LoginUserVo loginUserVo = loginUserService.findInfoById(userId);

		Equipment equipment = equipmentRepository.findTop1ById(id);

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备不存在");

		if(!equipment.getLoginUserId().equals(loginUserVo.getId()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "无权操作此设备");

		/**状态,{0:初始状态,1:共享中,2:使用中,3:取消共享,4:已停用,9:已删除}*/
		switch (equipment.getStatus()) {

		case 1:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备已共享,无需此操作");

		case 9:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		case 0:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "无权操作此设备");

		case 2:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备正在使用中,无法操作");

		case 4:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备已停用,无法操作");

		default:
			break;
		}

		EquipmentVo oldEquipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);
		EquipmentVo newEquipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);
		newEquipmentVo.setStatus(1);

		generateRecord(oldEquipmentVo, newEquipmentVo, 1, userId, id);

		if(equipmentRepository.updateStatus(id,equipment.getStatus(),1) != 1)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "共享失败,请稍后重试");

		return ResponseResult.SUCCESSM("共享设备成功");
	}

	@Override
	public ResponseResult cancelShare(Long id, Long userId) throws Exception {

		LoginUserVo loginUserVo = loginUserService.findInfoById(userId);

		Equipment equipment = equipmentRepository.findTop1ById(id);

		if(equipment == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备不存在");

		if(!equipment.getLoginUserId().equals(loginUserVo.getId()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "无权操作此设备");

		/**状态,{0:初始状态,1:共享中,2:使用中,3:取消共享,4:已停用,9:已删除}*/
		switch (equipment.getStatus()) {

		case 3:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备未共享,无需此操作");

		case 9:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备信息不存在");

		case 0:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "无权操作此设备");

		case 2:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备正在使用中,无法操作");

		case 4:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "设备已停用,无法操作");

		default:
			break;
		}

		EquipmentVo oldEquipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);
		EquipmentVo newEquipmentVo = BeanTools.setPropertiesToBean(equipment, EquipmentVo.class);
		newEquipmentVo.setStatus(3);

		generateRecord(oldEquipmentVo, newEquipmentVo, 1, userId, id);

		if(equipmentRepository.updateStatus(id,equipment.getStatus(),3) != 1)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "共享失败,请稍后重试");

		return ResponseResult.SUCCESSM("取消共享操作成功");
	}

}
