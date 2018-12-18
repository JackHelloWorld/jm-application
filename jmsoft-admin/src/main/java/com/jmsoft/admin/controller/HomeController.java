package com.jmsoft.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.user.service.AdminResourceService;
import com.jmsoft.user.vo.AdminResourceVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("home")
@Api(description="主页服务",tags="主服务")
public class HomeController extends BaseAdminController{

	private static final long serialVersionUID = 2284182118629887224L;

	@Reference
	private AdminResourceService adminResourceService;
	
	@PostMapping("find/menus")
	@ValidateAuth(validate=false)
	@ApiOperation(value="获取菜单",notes="只会返回当前登录用户菜单")
	public ResponseResult findMenus() throws BizException{
		List<AdminResourceVo> adminResources = adminResourceService.findUserResource(getUser().getId(), new Integer[]{0}, true);
		return ResponseResult.SUCCESS("获取菜单成功",adminResources);
	}
	
	@PostMapping("find/action")
	@ValidateAuth(validate=false)
	@ApiOperation(value="获取功能信息",notes="获取指定菜单下的功能")
	public ResponseResult findAction(@ApiParam(required=true,value="菜单id",defaultValue="0") @RequestParam(value="parent_id",defaultValue="0") Long parentId) throws BizException{
		List<AdminResourceVo> adminResources = adminResourceService.findUserResource(getUser().getId(), new Integer[]{1}, parentId,false);
		return ResponseResult.SUCCESS("获取功能资源成功",adminResources);
	}
	
}
