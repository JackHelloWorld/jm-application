package com.jm.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.exception.BizException;
import com.jm.sys.exception.NoException;
import com.jm.sys.exception.ParamException;
import com.jm.sys.service.BaseService;
import com.jm.sys.utils.AnnotationUtils;
import com.jm.sys.utils.PageBean;
import com.jm.sys.utils.PageQuery;
import com.jm.sys.utils.PageUtils;
import com.jm.sys.utils.Tools;
import com.jm.user.entity.AdminResource;
import com.jm.user.entity.AdminRole;
import com.jm.user.entity.AdminUser;
import com.jm.user.entity.AdminUserLoginLog;
import com.jm.user.entity.AdminUserStatusRecord;
import com.jm.user.mybatis.AuthDao;
import com.jm.user.mybatis.ListDao;
import com.jm.user.repository.AdminRoleRepository;
import com.jm.user.repository.AdminUserLoginLogRepository;
import com.jm.user.repository.AdminUserRepository;
import com.jm.user.repository.AdminUserStatusRecordRepository;
import com.jm.user.service.UserService;
import com.jm.user.service.utils.BaseUserService;
import com.jm.user.vo.AdminUserLoginVo;
import com.jm.user.vo.AdminUserProfileVo;
import com.jm.user.vo.AdminUserVo;
import com.jm.user.vo.UpdatePasswordVo;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl extends BaseUserService implements UserService {

	private static final long serialVersionUID = -6832818198752973704L;

	@Resource
	AdminRoleRepository adminRoleRepository;
	
	@Resource
	AuthDao authDao;

	@Reference
	private BaseService baseService;

	@Resource
	private ListDao listDao;

	@Value("${user.default.password}")
	private String userDefaultPassword;

	@Resource
	AdminUserLoginLogRepository adminUserLoginLogRepository;

	@Resource
	AdminUserStatusRecordRepository adminUserStatusRecordRepository;

	@Resource
	AdminUserRepository adminUserRepository;

	@Override
	public ResponseResult save(AdminUserVo adminUserVo) throws Exception {

		if(!Tools.isLong(adminUserVo.getCreateUserId()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作员有误");

		AdminUser actionUser = checkActionUserStatus(Long.parseLong(adminUserVo.getCreateUserId()));

		AdminUser adminUser = new AdminUser();

		validataEdit(adminUserVo, adminUser);

		adminUser.setCreateUserId(actionUser.getId());
		adminUser.setCreatetime(new Date());
		adminUser.setStatus(0);
		adminUser.setIsAdmin(false);

		adminUser.setLoginEncry(baseService.generateEncry(6));
		adminUser.setLoginPwd(Tools.MD5(userDefaultPassword, adminUser.getLoginEncry()));
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESSM("新增操作员成功");
	}

	private void validataEdit(AdminUserVo adminUserVo,AdminUser adminUser) throws  BizException, ParamException, NoException {

		AnnotationUtils.validateEdit(adminUserVo);

		adminUser.setAdminDesc(adminUserVo.getAdminDesc());
		adminUser.setNickName(adminUserVo.getNickName());
		adminUser.setHeadPortrait(adminUserVo.getHeadPortrait());
		adminUser.setPhone(adminUserVo.getPhone());

		if(adminUser.getId() == null){
			adminUser.setLoginName(adminUserVo.getLoginName().trim());
			long countUserByLoginName = adminUserRepository.countByLoginNameAndStatusIn(adminUser.getLoginName(),new Integer[]{0,1});
			if(countUserByLoginName > 0)
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "登录名已存在").throwBizException();
		}else{
			long countUserByLoginName = adminUserRepository.countByLoginNameAndStatusInAndIdNot(adminUser.getLoginName(),new Integer[]{0,1},adminUser.getId());
			if(countUserByLoginName > 0)
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "登录名已存在").throwBizException();

		}

		adminUser.setRoleId(Long.parseLong(adminUserVo.getRoleId()));
		AdminRole adminRole  = adminRoleRepository.findTop1ByIdAndStatusIn(adminUser.getRoleId(),new Integer[]{0,1});

		if(adminRole == null)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "选择角色不存在").throwParamException();

		if(adminRole.getStatus() == 1)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "选择角色已禁用").throwParamException();

	}

	@Override
	public ResponseResult delete(Long id,Long actionUserId) throws BizException {
		AdminUser actionUser = checkActionUserStatus(actionUserId);
		AdminUser adminUser = adminUserRepository.findTop1ById(id);
		if(adminUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "删除信息不存在");
		if(adminUser.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "删除信息不存在");
		generateAdminUserRecord(actionUserId, adminUser.getStatus(), 2, id);
		adminUser.setStatus(2);
		adminUser.setDeleteTime(new Date());
		adminUser.setDeleteUserId(actionUser.getId());
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESSM("删除信息成功");

	}

	@Override
	public ResponseResult update(AdminUserVo adminUserVo) throws Exception {

		if(!Tools.isLong(adminUserVo.getId()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "修改信息不存在");

		AdminUser adminUser = adminUserRepository.findTop1ById(Long.parseLong(adminUserVo.getId()));
		if(adminUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "修改信息不存在");

		/**账号状态,0:正常,1:注销,2:已经删除*/
		switch (adminUser.getStatus()) {

		case 1:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前信息已注销,无法修改");
		case 2:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前不存在,无法修改");

		default:
			break;

		}
		
		adminUserVo.setCreateUserId("0");

		validataEdit(adminUserVo, adminUser);
		if(!Tools.isLong(adminUserVo.getLastUpdateUserId()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作员有误");

		checkActionUserStatus(Long.parseLong(adminUserVo.getLastUpdateUserId()));

		adminUser.setLastUpdateTime(new Date());
		adminUser.setLastUpdateUserId(Long.parseLong(adminUserVo.getLastUpdateUserId()));
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESSM("修改操作员成功");

	}

	@Override
	public ResponseResult using(Long id, Long actionUserId) throws BizException {

		checkActionUserStatus(actionUserId);
		AdminUser adminUser = adminUserRepository.findTop1ById(id);
		if(adminUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作信息不存在");
		if(adminUser.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作信息不存在");

		if(adminUser.getStatus() == 0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户已启用,无需重复操作");

		adminUser.setStatus(0);
		generateAdminUserRecord(actionUserId, 1, 0, id);
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESSM("启用操作成功");

	}

	@Override
	public ResponseResult disabled(Long id, Long actionUserId) throws BizException {

		checkActionUserStatus(actionUserId);
		AdminUser adminUser = adminUserRepository.findTop1ById(id);
		if(adminUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作信息不存在");
		if(adminUser.getStatus() == 2)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作信息不存在");

		if(adminUser.getStatus() == 1)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前用户已停用,无需重复操作");

		adminUser.setStatus(1);
		generateAdminUserRecord(actionUserId, 0, 1, id);
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESSM("停用操作成功");

	}

	private void generateAdminUserRecord(Long actionUserId,Integer oldStatus,Integer newStatus,Long userId) {

		AdminUserStatusRecord adminUserStatusRecord = new AdminUserStatusRecord();
		adminUserStatusRecord.setAdminUserId(userId);
		adminUserStatusRecord.setCreateTime(new Date());
		adminUserStatusRecord.setCreateUserId(actionUserId);
		adminUserStatusRecord.setNewStatus(newStatus);
		adminUserStatusRecord.setOldStatus(oldStatus);
		adminUserStatusRecordRepository.save(adminUserStatusRecord);

	}

	@Override
	public ResponseResult login(AdminUserLoginVo adminUserLoginVo) throws Exception {

		AnnotationUtils.validateEdit(adminUserLoginVo);

		AdminUser adminUser = adminUserRepository.findTop1ByLoginNameAndStatusIn(adminUserLoginVo.getLogin_name().trim(),new Integer[]{0,1});


		if(adminUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户不存在");

		if(adminUser.getStatus() == 1)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户已禁用,无法登录");

		//检查角色
		if(adminUser.getIsAdmin() == null || !adminUser.getIsAdmin()){
			AdminRole adminRole = adminRoleRepository.findTop1ByIdAndStatusIn(adminUser.getRoleId(), new Integer[]{0,1});
			if(adminRole == null)
				ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前登录角色有误,无法操作").throwBizException();
			switch (adminRole.getStatus()) {
			case 1:
				ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前登录角色已停用,无法登录").throwBizException();

			default:
				break;
			}
			adminUser.setAdminRole(adminRole);
			adminUser.setRoleName(adminRole.getName());
		}

		String pwd = Tools.MD5(adminUserLoginVo.getPassword(), adminUser.getLoginEncry());
		if(!adminUser.getLoginPwd().equals(pwd))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户名或密码错误");
		Date date = new Date();
		adminUser.setLastLoginTime(date);

		AdminUserLoginLog adminUserLoginLog = new AdminUserLoginLog();
		adminUserLoginLog.setLoginTime(date);
		adminUserLoginLog.setLoginIp(adminUserLoginVo.getIp());
		adminUserLoginLog.setUserId(adminUser.getId());

		List<AdminResource> list = findUserResource(adminUser, new Integer[]{0,1}, false);

		//加载资源信息
		adminUser.setAdminResources(list);

		adminUserLoginLogRepository.save(adminUserLoginLog);
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESS("登录成功", adminUser);
	}

	@Override
	public PageBean webUserList(Integer pageNumber,Integer pageSize,AdminUserVo adminUserVo) throws Exception {
		AnnotationUtils.paramQuery(adminUserVo);
		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {

			@Override
			public List<?> query() {
				return listDao.webUserList(adminUserVo);
			}
		},AdminUser.class);
		return pageBean;
	}

	@Override
	public AdminUser findInfoById(Long id) throws BizException {
		AdminUser adminUser = checkActionUserStatus(id);
		return adminUser;
	}

	@Override
	public ResponseResult findUserResource(Long id, AdminUser adminUser) throws BizException {
		
		checkActionUserStatus(adminUser);
		
		AdminUser user = adminUserRepository.findTop1ByIdAndStatusNot(id, 2);
		if(user == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户不存在");
		
		/**账号状态,0:正常,1:停用,2:已经删除*/
		switch (user.getStatus()) {
		case 1:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户已停用无法操作资源");

		default:
			break;
		}
		
		if(user.getIsAdmin() != null && user.getIsAdmin())
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "无法操作管理员资源");
		
		//加载资源
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", user.getRoleId());
		List<AdminResource> adminResources = authDao.findRoleResourceByType(param);
		param.put("userId", user.getId());
		List<AdminResource> userResources = authDao.findThisResourceByType(param);
		
		for (AdminResource adminResource : adminResources) {
			for (AdminResource userResource : userResources) {
				if(adminResource.getId().equals(userResource.getId())){
					adminResource.setOwn(true);
					break;
				}
			}
		}
		
		List<AdminResource> data = initResource(adminResources, 0L);
		return ResponseResult.SUCCESS("获取资源成功", data);
	}

	@Override
	public ResponseResult countLogininfo(AdminUser adminUser) {
		
		if(adminUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户不存在");
		
		long count = adminUserLoginLogRepository.countByUserId(adminUser.getId());
		
		return ResponseResult.SUCCESS("获取登录次数成功", count);
	}

	@Override
	public PageBean findLogininfo(Integer pageNumber, Integer pageSize, AdminUser adminUser) throws Exception {
		
		if(adminUser == null)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "用户不存在").throwBizException();
		
		return PageUtils.query(pageNumber, pageSize, new PageQuery() {
			
			@Override
			public List<?> query() {
				return listDao.findLoginLog(adminUser.getId());
			}
		}, AdminUserLoginLog.class);
	}

	@Override
	public ResponseResult updateInfo(AdminUserProfileVo adminUserProfileVo) throws Exception {
		
		AnnotationUtils.validateEdit(adminUserProfileVo);
		
		AdminUser adminUser = checkActionUserStatus(adminUserProfileVo.getId());
		
		adminUser.setAddress(adminUserProfileVo.getAddress());
		adminUser.setIntro(adminUserProfileVo.getIntro());
		adminUser.setNickName(adminUserProfileVo.getNickName());
		adminUser.setPhone(adminUserProfileVo.getPhone());
		adminUserRepository.save(adminUser);
		
		return ResponseResult.SUCCESSM("资料修改成功");
	}

	@Override
	public ResponseResult updatePassword(UpdatePasswordVo updatePasswordVo) throws Exception {
		
		AnnotationUtils.validateEdit(updatePasswordVo);
		
		AdminUser adminUser = checkActionUserStatus(updatePasswordVo.getId());
		
		if(!Tools.MD5(updatePasswordVo.getOld_password().trim(), adminUser.getLoginEncry()).equals(adminUser.getLoginPwd()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "原密码输入错误");
		
		String checkPwd = "^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*]+$";
		
		if(!updatePasswordVo.getNew_password().trim().matches(checkPwd) || updatePasswordVo.getNew_password().trim().length() <= 8){
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "密码必须包含字母和数字或特殊字符并且长度大于8位");
		}
		
		adminUser.setLoginPwd(Tools.MD5(updatePasswordVo.getNew_password().trim(), adminUser.getLoginEncry()));
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESSM("密码修改成功");
	}

	@Override
	public ResponseResult updateProfile(String profile, AdminUser user) throws BizException {
		
		AdminUser adminUser = checkActionUserStatus(user.getId());
		
		if(Tools.isNullOrNullStr(profile))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "未上传头像信息,修改失败");
		
		adminUser.setHeadPortrait(profile.trim());
		adminUserRepository.save(adminUser);
		return ResponseResult.SUCCESSM("头像修改成功");
	}

}
