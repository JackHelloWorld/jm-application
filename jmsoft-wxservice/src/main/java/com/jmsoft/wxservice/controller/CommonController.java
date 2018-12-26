package com.jmsoft.wxservice.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.annotation.ValidateIgnoreLogin;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.sys.service.CommonService;
import com.jmsoft.sys.service.DictionaryService;
import com.jmsoft.sys.vo.FileVo;
import com.jmsoft.user.service.UserService;
import com.jmsoft.wxservice.utils.BaseWeChatController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("common")
@Api(description = "公共服务接口",tags="公共")
public class CommonController extends BaseWeChatController{

	private static final long serialVersionUID = 5036497442165052656L;
	
	@Reference
	CommonService commonService;
	
	@Reference
	DictionaryService dictionaryService;
	
	@Reference
	UserService userService;
	
	@ApiOperation("获取字典数据")
	@PostMapping("/get/address")
	@ValidateAuth(validate=false)
	public ResponseResult address(@ApiParam(name="parent_code",value="父级code",defaultValue="0") @RequestParam(value="parent_code",defaultValue="0")String parentCode){
		return commonService.findAddress(parentCode);
	}
	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ValidateIgnoreLogin
	@ApiOperation("上传图片")
	@PostMapping("upload/img")
	public ResponseResult uploadImg(@ApiParam(name="file",required=true,value="图片文件",allowMultiple=true) @RequestParam(value="file",required=false) MultipartFile file) throws Exception{
		
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
	@ApiOperation("上传文件")
	@PostMapping("upload/file")
	@ApiParam(name="file",required=true,value="文件数据",allowMultiple=true)
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
	 * 获取字典
	 * @param file
	 * @return
	 * @throws BizException 
	 * @throws Exception 
	 */
	@ApiOperation("获取字典")
	@ValidateAuth(validate=false)
	@PostMapping("find_dic")
	public ResponseResult findDic(@ApiParam(name="parentToken",value="父级token",defaultValue="0") @RequestParam(value="parentToken",defaultValue="0") String parentToken) throws BizException{
		return dictionaryService.findByParentToken(parentToken);
	}
	
}
