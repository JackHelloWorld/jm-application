package com.jmsoft.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.user.service.AdminResourceService;
import com.jmsoft.user.service.UserService;
import com.jmsoft.user.vo.AdminResourceVo;

@RestController
@RequestMapping("resource")
public class ResourceController extends BaseAdminController{

	private static final long serialVersionUID = 4311879306025300482L;
	
	@Reference
	AdminResourceService resourceService;
	
	@Reference
	UserService userService;
	
	@PostMapping("find")
	@ValidateAuth("/resource")
	public ResponseResult findAllResource() throws BizException{
		return ResponseResult.SUCCESS("资源获取成功", resourceService.findAllResource(getUser().getId()));
	}
	
	@PostMapping("delete")
	public ResponseResult delete(String id) throws BizException{
		return resourceService.delete(id,getUser().getId());
	}
	
	@PostMapping("update")
	public ResponseResult update(AdminResourceVo adminResource) throws Exception{
		return resourceService.update(adminResource,getUser().getId());
	}
	
	@PostMapping("save")
	public ResponseResult save(AdminResourceVo adminResource) throws Exception{
		return resourceService.save(adminResource,getUser().getId());
	}
	
}
