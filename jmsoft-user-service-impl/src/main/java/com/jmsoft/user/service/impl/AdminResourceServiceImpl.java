package com.jmsoft.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.data.ResultDic;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.common.exception.NoException;
import com.jmsoft.common.exception.ParamException;
import com.jmsoft.common.utils.AnnotationUtils;
import com.jmsoft.common.utils.BeanTools;
import com.jmsoft.common.utils.Tools;
import com.jmsoft.user.entity.AdminResource;
import com.jmsoft.user.entity.AdminUser;
import com.jmsoft.user.mybatis.AuthDao;
import com.jmsoft.user.repository.AdminResourceRepository;
import com.jmsoft.user.service.AdminResourceService;
import com.jmsoft.user.service.utils.BaseUserService;
import com.jmsoft.user.vo.AdminResourceVo;

@Service(retries=0,timeout=5000)
@Transactional(rollbackFor=Exception.class)
public class AdminResourceServiceImpl extends BaseUserService implements AdminResourceService  { 

	private static final long serialVersionUID = -8514405936502690688L;

	@Resource
	private AdminResourceRepository adminResourceRepository;

	@Resource
	private AuthDao authDao;

	@Override
	public List<AdminResourceVo> findUserResource(Long userId, Integer[] types, Long parentId, boolean dispose)
			throws BizException {

		AdminUser adminUser = checkActionUserStatus(userId);

		List<AdminResourceVo> list;

		if(adminUser.getIsAdmin() != null && adminUser.getIsAdmin()){
			List<AdminResource> resources = adminResourceRepository.findByParentIdAndTypeInOrderBySortAsc(parentId,types);
			list = new ArrayList<>();
			for (AdminResource adminResource : resources) {
				list.add(BeanTools.setPropertiesToBean(adminResource, AdminResourceVo.class));
			}
		}else{
			Map<String, Object> param = new HashMap<>();
			param.put("type", types);
			param.put("roleId", adminUser.getRoleId());
			param.put("userId", adminUser.getId());
			param.put("parentId", parentId);
			list = authDao.findThisResourceByType(param);
		}
		
		if(dispose)
			return  initResource(list, 0L);
		return list;
	
	}

	@Override
	public List<AdminResourceVo> findAllResource(Long userId) throws BizException {
		checkActionUserStatus(userId);
		List<AdminResource> adminResources = adminResourceRepository.findAll(new Sort(Direction.ASC, "sort"));
		List<AdminResourceVo> list = new ArrayList<>();
		for (AdminResource adminResource : adminResources) {
			AdminResourceVo adminResourceVo = new AdminResourceVo();
			BeanUtils.copyProperties(adminResource, adminResourceVo);
			list.add(adminResourceVo);
		}
		List<AdminResourceVo> result = initResource(list, 0L);
		return result;
	}
	
	@Override
	public ResponseResult delete(String id,Long userId) throws BizException {
		checkActionUserStatus(userId);
		if(!Tools.isLong(id))
			return ResponseResult.ERROR(ResultDic.DATA_WRONG);
		AdminResource adminResource = adminResourceRepository.getOne(Long.parseLong(id));
		
		if(adminResource == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在");
		adminResourceRepository.delete(adminResource);
		return ResponseResult.SUCCESS();
	}

	@Override
	public ResponseResult update(AdminResourceVo adminResourceVo,Long userId) throws Exception {
		
		AdminResource adminResource = BeanTools.setPropertiesToBean(adminResourceVo, AdminResource.class);
		
		checkActionUserStatus(userId);
		if(adminResource.getId() == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在");
		
		if(adminResourceRepository.countById(adminResource.getId()) == 0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在");
		
		validateInfo(adminResource);
		adminResourceRepository.save(adminResource);
		return ResponseResult.SUCCESS();
		
	}
	
	@Override
	public ResponseResult save(AdminResourceVo adminResourceVo,Long userId) throws Exception {
		
		AdminResource adminResource = BeanTools.setPropertiesToBean(adminResourceVo, AdminResource.class);
		
		checkActionUserStatus(userId);
		adminResource.setId(null);
		validateInfo(adminResource);
		adminResourceRepository.save(adminResource);
		return ResponseResult.SUCCESS();
	}
	
	private void validateInfo(AdminResource adminResource) throws ParamException, NoException, BizException{
		
		AnnotationUtils.validateEdit(adminResource);
		
		if(adminResource.getParentId() == null)
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "父资源选择错误").throwParamException();
		
		if(adminResource.getParentId() != 0){
			if(adminResourceRepository.countById(adminResource.getParentId()) == 0)
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "父资源选择错误").throwParamException();
		}
		
		if(!adminResource.getType().toString().matches("1|0"))
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "资源类型错误").throwParamException();
	
		if(adminResource.getType() == 1){
			if(Tools.isEmpty(adminResource.getClickAction()))
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当类型为按钮时,必须输入点击事件").throwParamException();
		}
	}

}
