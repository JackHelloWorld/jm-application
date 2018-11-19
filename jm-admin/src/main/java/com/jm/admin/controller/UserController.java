package com.jm.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jm.admin.utils.BaseAdminController;
import com.jm.sys.annotation.ValidateAuth;
import com.jm.sys.annotation.ValidateIgnoreLogin;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.utils.Tools;
import com.jm.sys.utils.VerifyCodeUtils;
import com.jm.user.entity.AdminUser;
import com.jm.user.service.UserService;
import com.jm.user.vo.AdminUserLoginVo;

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
	@PostMapping("login/code/initsession")
	public ResponseResult initsession() throws Exception{
		return ResponseResult.SUCCESS(Tools.MD5(UUID.randomUUID().toString(), System.currentTimeMillis()+""));
	}
	
	@ValidateIgnoreLogin
	@GetMapping("login/code")
	public void authImage(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="session_key",defaultValue="")String sessionKey) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		log.info("verifyCode:{}",verifyCode);
		
		if(Tools.isEmpty(sessionKey)) {
			log.info("sessionKey is null");
			return;
		}
		
		redisTemplate.opsForValue().set(sessionKey+"verCode", verifyCode.toLowerCase(), verCodeTimeout, TimeUnit.MINUTES);

		// 生成图片
		int w = 100, h = 30;
		OutputStream out = response.getOutputStream();
		VerifyCodeUtils.outputImage(w, h, out, verifyCode);
	}

	@ValidateIgnoreLogin
	@PostMapping("login")
	public ResponseResult login(AdminUserLoginVo adminUserLoginVo) throws Exception{

		if(Tools.isEmpty(adminUserLoginVo.getSession_key()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "验证码输入错误,或已过期");
		
		String verCode = (String) redisTemplate.opsForValue().get(adminUserLoginVo.getSession_key()+"verCode");
		
		if(verCode == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "验证码已过期,请重新获取");

		if(!verCode.trim().equalsIgnoreCase(adminUserLoginVo.getCode()))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "验证码输入错误");
		
		adminUserLoginVo.setIp(getIpAddress());
		
		ResponseResult responseResult =  userService.login(adminUserLoginVo);
		if(responseResult.getStatus()){
			AdminUser adminUser = (AdminUser) responseResult.getData();
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
	
}
