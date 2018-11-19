package com.jm.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jm.admin.utils.BaseAdminController;
import com.jm.sys.annotation.ValidateAuth;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.exception.BizException;
import com.jm.user.service.AdminRoleService;
import com.jm.user.service.UserService;
import com.jm.user.vo.AdminUserVo;

@RestController
@RequestMapping("webuser")
public class WebUserController extends BaseAdminController{

	private static final long serialVersionUID = 5770891007792934373L;
	
	@Reference
	private UserService userService;

	@Reference
	AdminRoleService adminRoleService;
	
	@PostMapping("list")
	public ResponseResult list(AdminUserVo adminUserVo,
			@RequestParam(value="pageNumber",defaultValue="1") Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10") Integer pageSize) throws Exception{
		return ResponseResult.SUCCESS("获取用户信息成功", userService.webUserList(pageNumber, pageSize, adminUserVo));
	}

	@PostMapping("find/roles")
	@ValidateAuth("/webuser")
	public ResponseResult findRoles(){
		return ResponseResult.SUCCESS("获取角色信息成功", adminRoleService.findAllRoles());
	}
	
	@PostMapping("save")
	public ResponseResult save(AdminUserVo adminUserVo) throws Exception{
		adminUserVo.setCreateUserId(getUser().getId().toString());
		return userService.save(adminUserVo);
	}
	
	@ValidateAuth("/webuser/update")
	@PostMapping("update/info")
	public ResponseResult updateInfo(@RequestParam(value="id",defaultValue="0")Long id) throws BizException{
		return ResponseResult.SUCCESS("获取用户信息成功", userService.findInfoById(id));
	}
	
	@PostMapping("update")
	public ResponseResult update(AdminUserVo adminUserVo) throws Exception{
		adminUserVo.setLastUpdateUserId(getUser().getId().toString());
		return userService.update(adminUserVo);
	}
	
	@PostMapping("success")
	public ResponseResult success(@RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userService.using(id, getUser().getId());
	}
	
	@PostMapping("block")
	public ResponseResult block(@RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userService.disabled(id, getUser().getId());
	}
	
	@PostMapping("delete")
	public ResponseResult delete(@RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userService.delete(id,getUser().getId());
	}
}
