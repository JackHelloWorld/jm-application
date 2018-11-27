package com.jm.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.jm.common.data.ResponseResult;
import com.jm.common.data.ResultCode;
import com.jm.common.exception.BizException;
import com.jm.common.exception.NoException;
import com.jm.common.exception.ParamException;
import com.jm.common.utils.AnnotationUtils;
import com.jm.common.utils.BeanTools;
import com.jm.common.utils.PageBean;
import com.jm.common.utils.PageQuery;
import com.jm.common.utils.PageUtils;
import com.jm.common.utils.Tools;
import com.jm.user.entity.AdminResource;
import com.jm.user.entity.AdminResourceRole;
import com.jm.user.entity.AdminResourceUser;
import com.jm.user.entity.AdminRole;
import com.jm.user.entity.AdminUser;
import com.jm.user.mybatis.AuthDao;
import com.jm.user.mybatis.ListDao;
import com.jm.user.repository.AdminResourceRepository;
import com.jm.user.repository.AdminResourceRoleRepository;
import com.jm.user.repository.AdminResourceUserRepository;
import com.jm.user.repository.AdminRoleRepository;
import com.jm.user.repository.AdminUserRepository;
import com.jm.user.service.AdminRoleService;
import com.jm.user.service.utils.BaseUserService;
import com.jm.user.vo.AdminResourceVo;
import com.jm.user.vo.AdminRoleVo;
import com.jm.user.vo.AdminUserDbVo;

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
	AdminResourceUserRepository adminResourceUserRepository;

	@Resource
	AdminResourceRepository adminResourceRepository;

	@Resource
	AdminUserRepository adminUserRepository;

	@Override
	public List<AdminRoleVo> findAllRoles() {
		List<AdminRole> adminRoles = adminRoleRepository.findByStatusOrderByCreateTimeDesc(0);
		List<AdminRoleVo> adminRoleVos = new ArrayList<AdminRoleVo>();
		for (AdminRole adminRole : adminRoles) {
			adminRoleVos.add(BeanTools.setPropertiesToBean(adminRole, AdminRoleVo.class));
		}
		return adminRoleVos;
	}

	public ResponseResult list(Integer pageNumber, Integer pageSize, AdminRoleVo adminRoleVo) throws Exception {

		AnnotationUtils.paramQuery(adminRoleVo);
		
		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {

			@Override
			public List<?> query() {
				List<Map<String, Object>> list = listDao.roleList(BeanTools.setPropertiesToBean(adminRoleVo, AdminRole.class));
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

	public ResponseResult update(AdminRoleVo adminRole,AdminUserDbVo adminUser) throws BizException, ParamException, NoException {

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

	public ResponseResult delete(Long id,AdminUserDbVo adminUser) throws BizException {

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

	public ResponseResult save(AdminRoleVo adminRoleVo,AdminUserDbVo adminUserDbVo) throws Exception {

		checkActionUserStatus(adminUserDbVo.getId());

		AnnotationUtils.validateEdit(adminRoleVo);
		
		AdminRole adminRole = BeanTools.setPropertiesToBean(adminRoleVo, AdminRole.class);

		adminRole.setCreateTime(new Date());
		adminRole.setName(adminRole.getName().trim());
		adminRole.setCreateUserId(adminUserDbVo.getId());
		adminRole.setStatus(0);
		long countByName = adminRoleRepository.countByNameAndStatusNot(adminRole.getName(),2);
		if(countByName > 0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "角色名称不能重复");
		adminRoleRepository.save(adminRole);
		return ResponseResult.SUCCESS();
	}

	public ResponseResult findResource(Long id,AdminUserDbVo adminUser) throws BizException {

		checkActionUserStatus(adminUser.getId());

		List<AdminResource> all = adminResourceRepository.findAll(new Sort(Direction.ASC, "sort"));
		List<AdminResourceVo> adminResources = new ArrayList<>();
		for (AdminResource adminResource : all) {
			adminResources.add(BeanTools.setPropertiesToBean(adminResource, AdminResourceVo.class));
		}
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", id);
		List<AdminResourceVo> resources = authDao.findRoleResourceByType(param);
		for (AdminResourceVo adminResource : adminResources) {
			for (AdminResourceVo node : resources) {
				if(node.getId().equals(adminResource.getId())){
					adminResource.setOwn(true);
					break;
				}
			}
		}
		return ResponseResult.SUCCESS(initResource(adminResources, 0L));
	}

	public ResponseResult resource(Long roleId, String[] ids, AdminUserDbVo adminUser) throws BizException {

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
	public ResponseResult success(Long id, AdminUserDbVo user) throws Exception {
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
	public ResponseResult block(Long id, AdminUserDbVo user) throws Exception {
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

	@Override
	public ResponseResult resourceUser(Long userId, String[] ids, AdminUserDbVo adminUser) throws BizException {

		checkActionUserStatus(adminUser.getId());

		AdminUser user = adminUserRepository.findTop1ByIdAndStatusNot(userId, 2);
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

		//删除资源配置
		adminResourceUserRepository.deleteByUserId(user.getId());

		Date actionDate = new Date();

		for (String id : ids) {
			if(!Tools.isLong(id))
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源信息选择错误").throwBizException();

			if(adminResourceRepository.countById(Long.parseLong(id)) == 0)
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在").throwBizException();

			//储存配置
			AdminResourceUser resourceUser = new AdminResourceUser();
			resourceUser.setCreateTime(actionDate);
			resourceUser.setCreateUser(adminUser.getId());
			resourceUser.setResourceId(Long.parseLong(id));
			resourceUser.setUserId(userId);
			adminResourceUserRepository.save(resourceUser);
		}

		return ResponseResult.SUCCESSM("授权操作成功");

	}

}
