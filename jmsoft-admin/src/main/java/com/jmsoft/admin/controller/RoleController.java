package com.jmsoft.admin.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultDic;
import com.jmsoft.common.utils.Tools;
import com.jmsoft.user.service.AdminRoleService;
import com.jmsoft.user.vo.AdminRoleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("role")
@Api(description="角色相关服务",tags="角色控制器")
public class RoleController extends BaseAdminController{

	private static final long serialVersionUID = 304774933228615052L;
	
	@Reference
	AdminRoleService adminRoleService;
	
	@PostMapping("list")
	@ApiOperation("获取列表")
	public ResponseResult list(@ApiParam(value="页大小",defaultValue="10") @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
			@ApiParam(value="页码",defaultValue="1") @RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,AdminRoleVo adminRole) throws Exception{

		return adminRoleService.list(pageNumber,pageSize,adminRole);
	}
	
	@PostMapping("update/info")
	@ApiOperation("根据id获取角色信息")
	@ValidateAuth("/role/update")
	public ResponseResult updateInfo(@ApiParam(value="角色id",required=true,type="Long") @RequestParam(value="id",defaultValue="")String id) throws NumberFormatException, Exception{
		if(!Tools.isLong(id))
			return ResponseResult.ERROR(ResultDic.DATA_WRONG);
		return adminRoleService.updateInfo(Long.parseLong(id));
	}
	
	@PostMapping("save")
	@ApiOperation(value="保存角色信息",notes="无需传入id")
	public ResponseResult save(AdminRoleVo adminRole) throws Exception{
		return adminRoleService.save(adminRole,getUser());
	}
	
	@PostMapping("delete")
	@ApiOperation(value="删除角色信息")
	public ResponseResult delete(@ApiParam(value="角色id",required=true,type="Long") @RequestParam(value="id",defaultValue="")String id) throws NumberFormatException, Exception{
		if(!Tools.isLong(id))
			return ResponseResult.ERROR(ResultDic.DATA_WRONG);
		return adminRoleService.delete(Long.parseLong(id),getUser());
	}
	
	@PostMapping("update")
	@ApiOperation(value="修改角色信息",notes="需传入id")
	public ResponseResult update(AdminRoleVo adminRole) throws Exception{
		return adminRoleService.update(adminRole,getUser());
	}
	
	@ValidateAuth("/role/resource")
	@PostMapping("find/resource")
	@ApiOperation(value="获取指定角色资源")
	public ResponseResult findResource(@ApiParam(value="角色id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return adminRoleService.findResource(id,getUser());
	}
	
	@PostMapping("resource")
	@ApiOperation(value="角色资源授权")
	public ResponseResult resource(@ApiParam(value="角色id",required=true) @RequestParam(value="id",defaultValue="0")Long id,@ApiParam(value="资源信息,以,隔开",required=true) @RequestParam(value="ids",defaultValue="")String ids) throws Exception{
		String[] ids1 = ids.split(",");
		return adminRoleService.resource(id,ids1,getUser());
	}
	
	@PostMapping("success")
	@ApiOperation(value="启用角色")
	public ResponseResult success(@ApiParam(value="角色id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return adminRoleService.success(id,getUser());
	}
	
	@ApiOperation(value="停用角色")
	@PostMapping("block")
	public ResponseResult block(@ApiParam(value="角色id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return adminRoleService.block(id,getUser());
	}
	
}
