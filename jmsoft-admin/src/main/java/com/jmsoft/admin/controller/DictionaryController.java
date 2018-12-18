package com.jmsoft.admin.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.sys.service.DictionaryService;
import com.jmsoft.sys.vo.DictionaryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("dictionary")
@Api(description="字典服务",tags="字典")
public class DictionaryController extends BaseAdminController{

	private static final long serialVersionUID = 6581399612861170554L;
	
	@Reference
	DictionaryService dictionaryService;
	
	@PostMapping("find")
	@ValidateAuth("/dictionary")
	@ApiOperation(value="获取字典",notes="无参")
	public ResponseResult findDictionaries(){
		return dictionaryService.findDictionaries();
	}
	
	@PostMapping("save")
	@ApiOperation(value="保存字典",notes="无需传入id")
	public ResponseResult save(DictionaryVo dictionary) throws Exception{
		return dictionaryService.save(dictionary);
	}
	
	@PostMapping("update")
	@ApiOperation(value="修改字典")
	public ResponseResult update(DictionaryVo dictionary) throws Exception{
		return dictionaryService.update(dictionary);
	}
	
	@PostMapping("delete")
	@ApiOperation(value="删除字典")
	public ResponseResult delete(@ApiParam(value="id",required=true) String id) throws Exception{
		return dictionaryService.delete(id);
	}
	
}
