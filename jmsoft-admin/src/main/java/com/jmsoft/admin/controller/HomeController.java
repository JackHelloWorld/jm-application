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

@RestController
@RequestMapping("home")
public class HomeController extends BaseAdminController{

	private static final long serialVersionUID = 2284182118629887224L;

	@Reference
	private AdminResourceService adminResourceService;
	
	@PostMapping("find/menus")
	@ValidateAuth(validate=false)
	public ResponseResult findMenus() throws BizException{
		List<AdminResourceVo> adminResources = adminResourceService.findUserResource(getUser().getId(), new Integer[]{0}, true);
		return ResponseResult.SUCCESS("获取菜单成功",adminResources);
	}
	
	@PostMapping("find/action")
	@ValidateAuth(validate=false)
	public ResponseResult findAction(@RequestParam(value="parent_id",defaultValue="0") Long parentId) throws BizException{
		List<AdminResourceVo> adminResources = adminResourceService.findUserResource(getUser().getId(), new Integer[]{1}, parentId,false);
		return ResponseResult.SUCCESS("获取功能资源成功",adminResources);
	}
	
}
