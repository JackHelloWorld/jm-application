package com.jm.sys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jasypt.encryption.StringEncryptor;

import com.alibaba.dubbo.config.annotation.Service;
import com.jm.sys.data.ConfigData;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.entity.Address;
import com.jm.sys.repository.AddressRepository;
import com.jm.sys.service.CommonService;
import com.jm.sys.utils.Tools;
import com.jm.sys.vo.FileVo;


@Service
public class CommonServiceImpl extends BaseServiceImpl implements CommonService{

	@Resource
	AddressRepository addressRepository;
	
	@Resource
	ConfigData configData;
	
	@Resource
	StringEncryptor encryptor;
	
	public ResponseResult findAddress(String parentCode) {
		List<Address> addresses = addressRepository.findByParentCode(parentCode);
		return ResponseResult.SUCCESS(addresses);
	}

	/**
	 * 参数加密
	 * @param value 明文
	 * @return
	 */
	public ResponseResult encrypt(String value) {

		if(Tools.notEmpty(value))
			return ResponseResult.SUCCESS(encryptor.encrypt(value));
		
		return ResponseResult.SUCCESS();
	}
	
	/**
	 * 参数解密
	 * @param value 密文
	 * @return
	 */
	public ResponseResult decrypt(String value) {
		
		if(Tools.notEmpty(value))
			return ResponseResult.SUCCESS(encryptor.decrypt(value));
		
		return ResponseResult.SUCCESS();
	}

	public ResponseResult uploadImg(FileVo fileVo) throws Exception {

		if(!fileVo.getContent_type().startsWith("image/"))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "请选择图片进行上传");
		
		String name = Tools.MD5(fileVo.getBytes()).concat(fileVo.getSuffix());
		String filePath = upload(fileVo.getBytes(), name);
		if(Tools.isEmpty(filePath))
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "图片上传失败").throwBizException();
		
		fileVo.setPath(filePath);
		fileVo.setName(name);
		fileVo.setService_path(configData.ftpImgPath);
		fileVo.setFull_path(configData.ftpImgPath.concat(filePath));
		fileVo.setUpload_time(new Date());
		return ResponseResult.SUCCESS(fileVo);
	}

	public ResponseResult uploadFile(FileVo fileVo) throws Exception {
		
		String name = Tools.MD5(fileVo.getBytes()).concat(fileVo.getSuffix());
		String filePath = upload(fileVo.getBytes(), name);
		if(Tools.isEmpty(filePath))
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "文件上传失败").throwBizException();
		
		fileVo.setPath(filePath);
		fileVo.setName(name);
		fileVo.setService_path(configData.ftpFilePath);
		fileVo.setFull_path(configData.ftpFilePath.concat(filePath));
		fileVo.setUpload_time(new Date());
		return ResponseResult.SUCCESS(fileVo);
	
	}
}
