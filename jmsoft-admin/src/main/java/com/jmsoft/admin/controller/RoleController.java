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

@RestController
@RequestMapping("role")
public class RoleController extends BaseAdminController{

	private static final long serialVersionUID = 304774933228615052L;
	
	@Reference
	AdminRoleService adminRoleService;
	
	@PostMapping("list")
	public ResponseResult list(@RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,AdminRoleVo adminRole) throws Exception{

		return adminRoleService.list(pageNumber,pageSize,adminRole);
	}
	
	@ValidateAuth("/role/update")
	@PostMapping("update/info")
	public ResponseResult updateInfo(@RequestParam(value="id",defaultValue="")String id) throws NumberFormatException, Exception{
		if(!Tools.isLong(id))
			return ResponseResult.ERROR(ResultDic.DATA_WRONG);
		return adminRoleService.updateInfo(Long.parseLong(id));
	}
	
	@PostMapping("save")
	public ResponseResult save(AdminRoleVo adminRole) throws Exception{
		return adminRoleService.save(adminRole,getUser());
	}
	
	@PostMapping("delete")
	public ResponseResult delete(@RequestParam(value="id",defaultValue="")String id) throws NumberFormatException, Exception{
		if(!Tools.isLong(id))
			return ResponseResult.ERROR(ResultDic.DATA_WRONG);
		return adminRoleService.delete(Long.parseLong(id),getUser());
	}
	
	@PostMapping("update")
	public ResponseResult update(AdminRoleVo adminRole) throws Exception{
		return adminRoleService.update(adminRole,getUser());
	}
	
	@ValidateAuth("/role/resource")
	@PostMapping("find/resource")
	public ResponseResult findResource(@RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return adminRoleService.findResource(id,getUser());
	}
	
	@PostMapping("resource")
	public ResponseResult resource(@RequestParam(value="id",defaultValue="0")Long id,@RequestParam(value="ids",defaultValue="")String ids) throws Exception{
		String[] ids1 = ids.split(",");
		return adminRoleService.resource(id,ids1,getUser());
	}
	
	@PostMapping("success")
	public ResponseResult success(@RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return adminRoleService.success(id,getUser());
	}
	
	@PostMapping("block")
	public ResponseResult block(@RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return adminRoleService.block(id,getUser());
	}
	
}
