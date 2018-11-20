package com.jm.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.exception.BizException;
import com.jm.sys.exception.NoException;
import com.jm.sys.exception.ParamException;
import com.jm.sys.utils.AnnotationUtils;
import com.jm.sys.utils.PageBean;
import com.jm.sys.utils.PageQuery;
import com.jm.sys.utils.PageUtils;
import com.jm.sys.utils.Tools;
import com.jm.user.entity.AdminResource;
import com.jm.user.entity.AdminResourceRole;
import com.jm.user.entity.AdminRole;
import com.jm.user.entity.AdminUser;
import com.jm.user.mybatis.AuthDao;
import com.jm.user.mybatis.ListDao;
import com.jm.user.repository.AdminResourceRepository;
import com.jm.user.repository.AdminResourceRoleRepository;
import com.jm.user.repository.AdminRoleRepository;
import com.jm.user.repository.AdminUserRepository;
import com.jm.user.service.AdminRoleService;
import com.jm.user.service.utils.BaseUserService;

@Service(timeout=60000)
@Transactional(rollbackFor=Exception.class)
public class AdminRoleServiceImpl  extends BaseUserService implements AdminRoleService{

	private static final long serialVersionUID = 7932281858437022915L;

	@Resource
	ListDao listDao;

	@Resource
	AdminRoleRepository adminRoleRepository;

	@Resource
	AuthDao authDao;

	@Resource
	AdminResourceRoleRepository adminResourceRoleRepository;

	@Resource
	AdminResourceRepository adminResourceRepository;

	@Resource
	AdminUserRepository adminUserRepository;

	@Override
	public List<AdminRole> findAllRoles() {
		return adminRoleRepository.findByStatusOrderByCreateTimeDesc(0);
	}

	public ResponseResult list(Integer pageNumber, Integer pageSize, AdminRole adminRole) throws Exception {

		AnnotationUtils.paramQuery(adminRole);

		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {

			@Override
			public List<?> query() {
				List<Map<String, Object>> list = listDao.roleList(adminRole);
				return list;
			}
		},AdminRole.class);
		return ResponseResult.SUCCESS(pageBean);
	}

	public ResponseResult updateInfo(Long id) {
		AdminRole adminRole = adminRoleRepository.findTop1ByIdAndStatusNot(id,2);
		if(adminRole == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		return ResponseResult.SUCCESS(adminRole);
	}

	public ResponseResult update(AdminRole adminRole,AdminUser adminUser) throws BizException, ParamException, NoException {

		checkActionUserStatus(adminUser.getId());

		AdminRole db = adminRoleRepository.findTop1ByIdAndStatusNot(adminRole.getId(), 2);

		if(db == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		AnnotationUtils.validateEdit(adminRole);

		if(adminRoleRepository.countByNameAndStatusNotAndIdNot(adminRole.getName().trim(),2,db.getId()) > 0)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "角色名称不能重复").throwBizException();

		db.setName(adminRole.getName().trim());
		db.setRemark(adminRole.getRemark());
		adminRoleRepository.save(db);
		return ResponseResult.SUCCESS();
	}

	public ResponseResult delete(Long id,AdminUser adminUser) throws BizException {

		checkActionUserStatus(adminUser.getId());

		AdminRole db = adminRoleRepository.findTop1ByIdAndStatusNot(id, 2);
		if(db == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		long count = adminUserRepository.countByRoleIdAndStatusNot(db.getId(),2);
		if(count > 0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前角色包含"+count+"个用户,无法删除");

		db.setStatus(2);
		adminRoleRepository.save(db);
		return ResponseResult.SUCCESS();
	}

	public ResponseResult save(AdminRole adminRole,AdminUser adminUser) throws Exception {

		checkActionUserStatus(adminUser.getId());

		AnnotationUtils.validateEdit(adminRole);

		adminRole.setCreateTime(new Date());
		adminRole.setName(adminRole.getName().trim());
		adminRole.setCreateUserId(adminUser.getId());
		adminRole.setStatus(0);
		long countByName = adminRoleRepository.countByNameAndStatusNot(adminRole.getName(),2);
		if(countByName > 0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "角色名称不能重复");
		adminRoleRepository.save(adminRole);
		return ResponseResult.SUCCESS();
	}

	public ResponseResult findResource(Long id,AdminUser adminUser) throws BizException {

		checkActionUserStatus(adminUser.getId());

		List<AdminResource> adminResources = adminResourceRepository.findAll(new Sort(Direction.ASC, "sort"));
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", id);
		List<AdminResource> resources = authDao.findThisResourceByType(param);
		for (AdminResource adminResource : adminResources) {
			for (AdminResource node : resources) {
				if(node.getId().equals(adminResource.getId())){
					adminResource.setOwn(true);
					break;
				}
			}
		}
		return ResponseResult.SUCCESS(initResource(adminResources, 0L));
	}

	public ResponseResult resource(Long roleId, String[] ids, AdminUser adminUser) throws BizException {

		checkActionUserStatus(adminUser.getId());

		AdminRole db = adminRoleRepository.findTop1ByIdAndStatusNot(roleId, 2);
		if(db == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		//删除资源配置
		adminResourceRoleRepository.deleteByRoleId(roleId);

		Date actionDate = new Date();

		for (String id : ids) {
			if(!Tools.isLong(id))
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源信息选择错误").throwBizException();

			if(adminResourceRepository.countById(Long.parseLong(id)) == 0)
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在").throwBizException();

			//储存配置
			AdminResourceRole resourceRole = new AdminResourceRole();
			resourceRole.setCreateTime(actionDate);
			resourceRole.setCreateUser(adminUser.getId());
			resourceRole.setResourceId(Long.parseLong(id));
			resourceRole.setRoleId(roleId);
			adminResourceRoleRepository.save(resourceRole);
		}

		return ResponseResult.SUCCESS();

	}

	@Override
	public ResponseResult success(Long id, AdminUser user) throws Exception {
		checkActionUserStatus(user.getId());

		AdminRole adminRole = adminRoleRepository.findTop1ByIdAndStatusNot(id, 2);

		if(adminRole == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作信息不存在");

		switch (adminRole.getStatus()) {
		case 0:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前角色已启用,无需重复操作");

		default:
			if(adminRole.getStatus() != 1)
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前角色信息有误,请联系管理员");
			break;
		}
		adminRole.setStatus(0);
		adminRoleRepository.save(adminRole);
		return ResponseResult.SUCCESSM("启用信息成功");
	}

	@Override
	public ResponseResult block(Long id, AdminUser user) throws Exception {
		checkActionUserStatus(user.getId());

		AdminRole adminRole = adminRoleRepository.findTop1ByIdAndStatusNot(id, 2);

		if(adminRole == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "操作信息不存在");

		switch (adminRole.getStatus()) {
		case 1:
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前角色已停用,无需重复操作");

		default:
			if(adminRole.getStatus() != 0)
				return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前角色信息有误,请联系管理员");
			break;
		}
		adminRole.setStatus(1);
		adminRoleRepository.save(adminRole);
		return ResponseResult.SUCCESSM("停用信息成功");
	}

}
