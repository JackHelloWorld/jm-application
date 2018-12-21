package com.jmsoft.admin.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.user.service.LoginUserService;
import com.jmsoft.user.vo.LoginUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("loginuser")
@Api(description="前台用户相关服务",tags="前台用户控制器")
public class LoginUserController extends BaseAdminController{

	private static final long serialVersionUID = 304774933228615052L;
	
	@Reference
	LoginUserService loginUserService;
	
	@PostMapping("list")
	@ApiOperation("获取列表")
	public ResponseResult list(@ApiParam(value="页大小",defaultValue="10") @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
			@ApiParam(value="页码",defaultValue="1") @RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,LoginUserVo loginUserVo) throws Exception{

		return loginUserService.list(loginUserVo,pageNumber,pageSize);
	}
		
	@PostMapping("update")
	@ApiOperation(value="修改信息",notes="无需传入id")
	public ResponseResult update(LoginUserVo loginUserVo) throws Exception{
		return loginUserService.updateInfo(loginUserVo);
	}
	
	@PostMapping("delete")
	@ApiOperation(value="删除信息")
	public ResponseResult delete(@ApiParam(value="用户id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws NumberFormatException, Exception{
		return loginUserService.delete(id,getUser());
	}
	
	@PostMapping("success")
	@ApiOperation(value="启用用户")
	public ResponseResult success(@ApiParam(value="用户id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return loginUserService.success(id,getUser());
	}
	
	@ApiOperation(value="停用用户")
	@PostMapping("block")
	public ResponseResult block(@ApiParam(value="用户id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return loginUserService.block(id,getUser());
	}
	
}
