package com.jm.user.service.impl;

import java.util.Date;
import java.util.List;

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
import com.jm.user.mybatis.ListDao;
import com.jm.user.repository.AdminRoleRepository;
import com.jm.user.repository.AdminUserLoginLogRepository;
import com.jm.user.repository.AdminUserRepository;
import com.jm.user.repository.AdminUserStatusRecordRepository;
import com.jm.user.service.UserService;
import com.jm.user.service.utils.BaseUserService;
import com.jm.user.vo.AdminUserLoginVo;
import com.jm.user.vo.AdminUserVo;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl extends BaseUserService implements UserService {

	private static final long serialVersionUID = -6832818198752973704L;

	@Resource
	AdminRoleRepository adminRoleRepository;

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
			AdminRole adminRole = adminRoleRepository.findTop1ByIdAndStatusIn(adminUser.getRoleId(), new Integer[]{0});
			if(adminRole == null)
				ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode, "当前登录角色有误,无法操作").throwBizException();
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

}
