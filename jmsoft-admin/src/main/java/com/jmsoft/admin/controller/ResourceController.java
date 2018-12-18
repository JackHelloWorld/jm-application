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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("resource")
@Api(description="系统资源服务",tags="资源控制器")
public class ResourceController extends BaseAdminController{

	private static final long serialVersionUID = 4311879306025300482L;
	
	@Reference
	AdminResourceService resourceService;
	
	@Reference
	UserService userService;
	
	@PostMapping("find")
	@ValidateAuth("/resource")
	@ApiOperation(value="获取所有资源")
	public ResponseResult findAllResource() throws BizException{
		return ResponseResult.SUCCESS("资源获取成功", resourceService.findAllResource(getUser().getId()));
	}
	
	@PostMapping("delete")
	@ApiOperation(value="删除指定资源")
	public ResponseResult delete(@ApiParam(value="资源id",required=true) String id) throws BizException{
		return resourceService.delete(id,getUser().getId());
	}
	
	@PostMapping("update")
	@ApiOperation(notes="id不能为空",value="修改资源信息")
	public ResponseResult update(AdminResourceVo adminResource) throws Exception{
		return resourceService.update(adminResource,getUser().getId());
	}
	
	@PostMapping("save")
	@ApiOperation(notes="无需传入id",value="保存资源信息")
	public ResponseResult save(AdminResourceVo adminResource) throws Exception{
		return resourceService.save(adminResource,getUser().getId());
	}
	
}
