package com.jmsoft.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.common.utils.AnnotationUtils;
import com.jmsoft.common.utils.BeanTools;
import com.jmsoft.common.utils.PageBean;
import com.jmsoft.common.utils.PageQuery;
import com.jmsoft.common.utils.PageUtils;
import com.jmsoft.common.utils.Tools;
import com.jmsoft.equipment.service.EquipmentService;
import com.jmsoft.sys.config.DictionaryCode;
import com.jmsoft.sys.service.DictionaryService;
import com.jmsoft.user.entity.LoginUser;
import com.jmsoft.user.entity.LoginUserStatusRecord;
import com.jmsoft.user.mybatis.LoginUserDao;
import com.jmsoft.user.repository.LoginUserRepository;
import com.jmsoft.user.repository.LoginUserStatusRecordRepository;
import com.jmsoft.user.service.LoginUserService;
import com.jmsoft.user.service.utils.BaseUserService;
import com.jmsoft.user.vo.AdminUserDbVo;
import com.jmsoft.user.vo.LoginUserVo;

@Service(timeout=60000)
@Transactional(rollbackFor=Exception.class)
public class LoginUserServiceImpl extends BaseUserService implements LoginUserService{

	private static final long serialVersionUID = -49295865581840483L;

	@Resource
	LoginUserDao loginUserDao;

	@Reference(check=false)
	EquipmentService equipmentService;

	@Resource
	LoginUserRepository loginUserRepository;

	@Resource
	LoginUserStatusRecordRepository loginUserStatusRecordRepository;

	@Reference
	DictionaryService dictionaryService;

	@Override
	public ResponseResult list(LoginUserVo loginUserVo, Integer pageNumber, Integer pageSize) throws Exception {

		AnnotationUtils.paramQuery(loginUserVo);

		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {

			@Override
			public List<Map<String, Object>> query() {
				return loginUserDao.list(loginUserVo);
			}
		}, LoginUserVo.class);

		return ResponseResult.SUCCESS("获取用户信息成功", pageBean);
	}

	@Override
	public ResponseResult updateInfo(LoginUserVo loginUserVo,boolean updateType) throws Exception {

		AnnotationUtils.validateEdit(loginUserVo);

		if(loginUserVo.getId() == null) 
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		LoginUser loginUser = BeanTools.setPropertiesToBean(loginUserVo, LoginUser.class);

		//字典检查
		if(!dictionaryService.checkValueAndParentToken(loginUser.getCertificateType(),DictionaryCode.CERTIFICATE_TYPE)) {
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "证件类型选择错误");
		}

		if(!dictionaryService.checkValueAndParentToken(loginUser.getSex(),DictionaryCode.SEX)) {
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "性别选择错误");
		}

		LoginUser dbUser = loginUserRepository.findTop1ByIdAndStatusIn(loginUser.getId(),new Integer[]{0,1});

		if(dbUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		loginUser.setAlipayOpenId(dbUser.getAlipayOpenId());
		loginUser.setStatus(dbUser.getStatus());
		loginUser.setUpdateTime(new Date());
		loginUser.setCreateTime(dbUser.getCreateTime());
		loginUser.setWxOpenId(dbUser.getWxOpenId());
		if(Tools.isEmpty(loginUser.getProfileImg()))
			loginUser.setProfileImg(dbUser.getProfileImg());

		if(updateType) {
			if(!loginUser.getUserType().toString().matches("0|1|2|3"))
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "类型选择错误");
			if(loginUser.getUserType().toString().matches("0|1")) {
				if(Tools.isEmpty(loginUser.getAlipayOpenId()) && Tools.isEmpty(loginUser.getWxOpenId()))
					return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前账户未绑定,无法修改为商家或普通客户");
			}

			if(!dbUser.getUserType().equals(loginUser.getUserType())) {

				switch (dbUser.getUserType()) {
				case 1:
					if(equipmentService.checkUser(dbUser.getId())) {
						return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备,无法从商家修改为其他类型");
					}
					break;
				case 2:
					if(equipmentService.checkCityInfo(dbUser.getId())) {
						return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备进行市级代理配置,无法从商家修改为其他类型");
					}
					break;
				case 3:
					if(equipmentService.checkCountyInfo(dbUser.getId())) {
						return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备进行县级代理配置,无法从商家修改为其他类型");
					}
					break;

				default:
					break;
				}
			}


		}else {
			loginUser.setUserType(dbUser.getUserType());
		}

		loginUserRepository.save(loginUser);
		return ResponseResult.SUCCESSM("信息编辑成功");
	}

	@Override
	public ResponseResult delete(Long id, AdminUserDbVo user) {

		LoginUser loginUser = loginUserRepository.findTop1ByIdAndStatusIn(id, new Integer[]{0,1});
		if(loginUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在,无法删除");

		switch (loginUser.getUserType()) {
		case 1:
			if(equipmentService.checkUser(loginUser.getId())) {
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备,无法删除当前用户");
			}
			break;
		case 2:
			if(equipmentService.checkCityInfo(loginUser.getId())) {
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备进行市级代理配置,无法删除当前用户");
			}
			break;
		case 3:
			if(equipmentService.checkCountyInfo(loginUser.getId())) {
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备进行县级代理配置,无法删除当前用户");
			}
			break;

		default:
			break;
		}

		//生成记录
		LoginUserStatusRecord loginUserStatusRecord = new LoginUserStatusRecord(null,id,user.getId(),2,loginUser.getStatus(),new Date());

		loginUserStatusRecordRepository.save(loginUserStatusRecord);

		loginUser.setStatus(2);
		loginUser.setDeleteTime(new Date());

		loginUserRepository.save(loginUser);

		return ResponseResult.SUCCESSM("删除成功");
	}

	@Override
	public ResponseResult success(Long id, AdminUserDbVo user) {

		LoginUser loginUser = loginUserRepository.findTop1ByIdAndStatusIn(id, new Integer[]{0,1});
		if(loginUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在,无法删除");

		if(loginUser.getStatus() == 0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前信息正常,无需重复启用");

		//生成记录
		LoginUserStatusRecord loginUserStatusRecord = new LoginUserStatusRecord(null,id,user.getId(),0,loginUser.getStatus(),new Date());

		loginUserStatusRecordRepository.save(loginUserStatusRecord);

		loginUser.setStatus(0);

		loginUserRepository.save(loginUser);

		return ResponseResult.SUCCESSM("启用成功");

	}

	@Override
	public ResponseResult block(Long id, AdminUserDbVo user) {

		LoginUser loginUser = loginUserRepository.findTop1ByIdAndStatusIn(id, new Integer[]{0,1});
		if(loginUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在,无法删除");

		if(loginUser.getStatus() == 1)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前已停用,无需重复操作");

		switch (loginUser.getUserType()) {
		case 1:
			if(equipmentService.checkUser(loginUser.getId())) {
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备,无法停用当前用户");
			}
			break;
		case 2:
			if(equipmentService.checkCityInfo(loginUser.getId())) {
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备进行市级代理配置,无法停用当前用户");
			}
			break;
		case 3:
			if(equipmentService.checkCountyInfo(loginUser.getId())) {
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户下有设备进行县级代理配置,无法停用当前用户");
			}
			break;

		default:
			break;
		}

		//生成记录
		LoginUserStatusRecord loginUserStatusRecord = new LoginUserStatusRecord(null,id,user.getId(),1,loginUser.getStatus(),new Date());

		loginUserStatusRecordRepository.save(loginUserStatusRecord);

		loginUser.setStatus(1);

		loginUserRepository.save(loginUser);

		return ResponseResult.SUCCESSM("停用成功");

	}

	@Override
	public ResponseResult save(LoginUserVo loginUserVo) throws Exception {

		AnnotationUtils.validateEdit(loginUserVo);

		LoginUser loginUser = BeanTools.setPropertiesToBean(loginUserVo, LoginUser.class);
		loginUser.setId(null);

		//字典检查
		if(!dictionaryService.checkValueAndParentToken(loginUser.getCertificateType(),DictionaryCode.CERTIFICATE_TYPE)) {
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "证件类型选择错误");
		}

		if(!dictionaryService.checkValueAndParentToken(loginUser.getSex(),DictionaryCode.SEX)) {
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "性别选择错误");
		}

		if(!loginUser.getUserType().toString().matches("2|3"))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "类型选择错误");

		loginUser.setAlipayOpenId(null);
		if(Tools.isEmpty(loginUser.getBankName()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "请输入开户行名称");

		if(Tools.isEmpty(loginUser.getBankNo()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "请输入卡号");

		loginUser.setCreateTime(new Date());
		loginUser.setDeleteTime(null);
		loginUser.setStatus(0);
		loginUser.setUpdateTime(null);
		loginUser.setWxOpenId(null);

		loginUserRepository.save(loginUser);
		return ResponseResult.SUCCESSM("保存代理成功");

	}

	@Override
	public boolean isCityUser(Long userId) {
		return loginUserRepository.countByIdAndStatusAndUserType(userId,0,2) > 0;
	}

	@Override
	public boolean isCountyUser(Long userId) {
		return loginUserRepository.countByIdAndStatusAndUserType(userId,0,3) > 0;
	}

	@Override
	public ResponseResult findListByType(Integer[] type) {

		List<LoginUser> list = loginUserRepository.findByStatusAndUserTypeInOrderByUpdateTimeDesc(0,type);

		List<LoginUserVo> loginUserVos = new ArrayList<>();

		for (LoginUser loginUser : list) 
			loginUserVos.add(BeanTools.setPropertiesToBean(loginUser, LoginUserVo.class));

		return ResponseResult.SUCCESS("获取用户信息成功",loginUserVos);
	}

	@Override
	public LoginUserVo initLoginUserByWeChatOpenId(String openid) throws BizException {

		if(openid == null)
			return null;

		LoginUser loginUser = loginUserRepository.findTop1ByWxOpenIdAndStatusIn(openid,new Integer[] {0,1});

		//初始化用户
		if(loginUser == null) {
			loginUser = new LoginUser();
			loginUser.setCreateTime(new Date());
			loginUser.setUserType(0);
			loginUser.setStatus(0);
			loginUser.setWxOpenId(openid);
			loginUserRepository.save(loginUser);
		}else {
			if (loginUser.getStatus() == 1) 
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息已禁用,请联系管理员").throwBizException();
		}
		
		return BeanTools.setPropertiesToBean(loginUser, LoginUserVo.class);
	}

	@Override
	public LoginUserVo findInfoById(Long userId) throws BizException {
		
		LoginUser loginUser = loginUserRepository.findTop1ByIdAndStatusIn(userId, new Integer[] {0,1});
		if(loginUser == null) 
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户不存在").throwBizException();
		if(loginUser.getStatus() == 1)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户已被禁用").throwBizException();
		
		return BeanTools.setPropertiesToBean(loginUser, LoginUserVo.class);
	}

}
