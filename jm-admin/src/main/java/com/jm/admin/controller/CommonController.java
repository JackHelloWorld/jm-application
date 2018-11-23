package com.jm.admin.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jm.admin.utils.BaseAdminController;
import com.jm.sys.annotation.ValidateAuth;
import com.jm.sys.annotation.ValidateIgnoreLogin;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.exception.BizException;
import com.jm.sys.service.CommonService;
import com.jm.sys.vo.FileVo;
import com.jm.user.service.UserService;


@RestController
@RequestMapping("common")
public class CommonController extends BaseAdminController{

	private static final long serialVersionUID = 5036497442165052656L;
	
	@Reference
	CommonService commonService;
	
	@Reference
	UserService userService;
	
	@PostMapping("/get/address")
	@ValidateAuth(validate=false)
	public ResponseResult address(@RequestParam(value="parent_code",defaultValue="0")String parentCode){
		return commonService.findAddress(parentCode);
	}
	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ValidateIgnoreLogin
	@PostMapping("upload/img")
	public ResponseResult uploadImg(@RequestParam(value="file",required=false) MultipartFile file) throws Exception{
		
		if(file == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "未选择图片");
		
		FileVo fileVo = new FileVo();
		fileVo.setBytes(file.getBytes());
		fileVo.setContent_type(file.getContentType());
		fileVo.setOld_name(file.getOriginalFilename());
		
		return commonService.uploadImg(fileVo);
	}
	
	/**
	 * 普通文件上传
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ValidateIgnoreLogin
	@PostMapping("upload/file")
	public ResponseResult uploadFile(@RequestParam(value="file",required=false) MultipartFile file) throws Exception{
		
		if(file == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "未选择文件");
		
		FileVo fileVo = new FileVo();
		fileVo.setBytes(file.getBytes());
		fileVo.setContent_type(file.getContentType());
		fileVo.setOld_name(file.getOriginalFilename());
		
		return commonService.uploadFile(fileVo);
	
	}
	
	/**
	 * 修改头像
	 * @param file
	 * @return
	 * @throws BizException 
	 * @throws Exception 
	 */
	@ValidateAuth(validate=false)
	@PostMapping("update_profile")
	public ResponseResult updateProfile(@RequestParam(value="profile",defaultValue="") String profile) throws BizException{
		return userService.updateProfile(profile,getUser());
		
	}
	
}
