package com.jmsoft.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.user.service.AdminRoleService;
import com.jmsoft.user.service.UserService;
import com.jmsoft.user.vo.AdminUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("webuser")
@Api(description="后台用户管理",tags="系统用户")
public class WebUserController extends BaseAdminController{

	private static final long serialVersionUID = 5770891007792934373L;
	
	@Reference
	private UserService userService;

	@Reference
	AdminRoleService adminRoleService;
	
	@PostMapping("list")
	@ApiOperation("获取用户列表")
	public ResponseResult list(AdminUserVo adminUserVo,
			@ApiParam(defaultValue="1",value="页码") @RequestParam(value="pageNumber",defaultValue="1") Integer pageNumber,
			@ApiParam(defaultValue="10",value="页大小") @RequestParam(value="pageSize",defaultValue="10") Integer pageSize) throws Exception{
		return ResponseResult.SUCCESS("获取用户信息成功", userService.webUserList(pageNumber, pageSize, adminUserVo));
	}

	@PostMapping("find/roles")
	@ValidateAuth("/webuser")
	@ApiOperation("获取角色列表")
	public ResponseResult findRoles(){
		return ResponseResult.SUCCESS("获取角色信息成功", adminRoleService.findAllRoles());
	}
	
	@PostMapping("save")
	@ApiOperation("保存角色")
	public ResponseResult save(AdminUserVo adminUserVo) throws Exception{
		adminUserVo.setCreateUserId(getUser().getId().toString());
		return userService.save(adminUserVo);
	}
	
	@ValidateAuth("/webuser/update")
	@PostMapping("update/info")
	@ApiOperation(value="根据id获取用户信息")
	public ResponseResult updateInfo(@ApiParam(required=true,value="用户id") @RequestParam(value="id",defaultValue="0")Long id) throws BizException{
		return ResponseResult.SUCCESS("获取用户信息成功", userService.findInfoById(id));
	}
	
	@PostMapping("update")
	@ApiOperation("修改用户信息")
	public ResponseResult update(AdminUserVo adminUserVo) throws Exception{
		adminUserVo.setLastUpdateUserId(getUser().getId().toString());
		return userService.update(adminUserVo);
	}
	
	@PostMapping("find/resource")
	@ValidateAuth("/webuser/resource")
	@ApiOperation("获取资源,并标记指定用户")
	public ResponseResult findUserResource(@ApiParam(required=true,value="用户id") @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return userService.findUserResource(id,getUser());
	}
	
	@PostMapping("resource")
	@ApiOperation("用户资源授权")
	public ResponseResult resource(@ApiParam(required=true,value="用户id") @RequestParam(value="id",defaultValue="0")Long id,@ApiParam(required=true,value="资源信息,以,隔开") @RequestParam(value="ids",defaultValue="")String ids) throws Exception{
		String[] ids1 = ids.split(",");
		return adminRoleService.resourceUser(id,ids1,getUser());
	}
	
	@PostMapping("success")
	@ApiOperation("启用用户")
	public ResponseResult success(@ApiParam(required=true,value="用户id") @RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userService.using(id, getUser().getId());
	}
	
	@PostMapping("block")
	@ApiOperation("停用用户")
	public ResponseResult block(@ApiParam(required=true,value="用户id") @RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userService.disabled(id, getUser().getId());
	}
	
	@PostMapping("delete")
	@ApiOperation("删除用户")
	public ResponseResult delete(@ApiParam(required=true,value="用户id") @RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userService.delete(id,getUser().getId());
	}
}
