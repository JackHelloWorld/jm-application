package com.jmsoft.admin.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.annotation.ValidateIgnoreLogin;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.utils.ControllerUtils;
import com.jmsoft.common.utils.PageBean;
import com.jmsoft.common.utils.Tools;
import com.jmsoft.user.service.UserService;
import com.jmsoft.user.vo.AdminUserDbVo;
import com.jmsoft.user.vo.AdminUserLoginVo;
import com.jmsoft.user.vo.AdminUserProfileVo;
import com.jmsoft.user.vo.UpdatePasswordVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseAdminController{

	private static final long serialVersionUID = -7714059355046808702L;

	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
	/**后台用户登录超时时间(分)*/
	@Value("${user.web.login.timeout}")
	private Long loginTimeout;

	/**验证码过期时间,(分)*/
	@Value("${admin.code.timeout}")
	private Long verCodeTimeout;

	@Reference
	UserService userService;

	@ValidateIgnoreLogin
	@PostMapping("login")
	public ResponseResult login(AdminUserLoginVo adminUserLoginVo,HttpServletRequest request) throws Exception{
		
		adminUserLoginVo.setIp(ControllerUtils.getIpAddress(request));
		
		ResponseResult responseResult =  userService.login(adminUserLoginVo);
		if(responseResult.getStatus()){
			AdminUserDbVo adminUser = (AdminUserDbVo) responseResult.getData();
			String token = Tools.MD5(adminUser.getId().toString(), System.currentTimeMillis()+"");
			adminUser.setToken(token);
			redisTemplate.opsForValue().set(token, adminUser, loginTimeout, TimeUnit.MINUTES);
			log.info("登录成功,token:{}", token);
		}

		return responseResult;
	}
	
	@PostMapping("logout")
	@ValidateAuth(validate=false)
	public ResponseResult logout(){
		String token = getUser().getToken();
		redisTemplate.delete(token);
		return ResponseResult.SUCCESSM("退出成功");
	}
	
	@PostMapping("count/logininfo")
	@ValidateAuth(validate=false)
	public ResponseResult countLogininfo(){
		return userService.countLogininfo(getUser());
	}
	
	@PostMapping("find/logininfo")
	@ValidateAuth(validate=false)
	public ResponseResult findLogininfo(@RequestParam(value="pageNumber",defaultValue="1") Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10") Integer pageSize) throws Exception{
		PageBean pageBean = userService.findLogininfo(pageNumber,pageSize,getUser());
		return ResponseResult.SUCCESS("获取登录记录成功", pageBean);
	}
	
	@PostMapping("update/profile")
	@ValidateAuth(validate=false)
	public ResponseResult update(AdminUserProfileVo adminUserProfileVo) throws Exception{
		adminUserProfileVo.setId(getUser().getId());
		return userService.updateInfo(adminUserProfileVo);
	}
	
	@PostMapping("update/password")
	@ValidateAuth(validate=false)
	public ResponseResult updatePassword(UpdatePasswordVo updatePasswordVo) throws Exception{
		updatePasswordVo.setId(getUser().getId());
		return userService.updatePassword(updatePasswordVo);
	}
	
}
