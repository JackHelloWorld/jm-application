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

@RestController
@RequestMapping("dictionary")
public class DictionaryController extends BaseAdminController{

	private static final long serialVersionUID = 6581399612861170554L;
	
	@Reference
	DictionaryService dictionaryService;
	
	@PostMapping("find")
	@ValidateAuth("/dictionary")
	public ResponseResult findDictionaries(){
		return dictionaryService.findDictionaries();
	}
	
	@PostMapping("save")
	public ResponseResult save(DictionaryVo dictionary) throws Exception{
		return dictionaryService.save(dictionary);
	}
	
	@PostMapping("update")
	public ResponseResult update(DictionaryVo dictionary) throws Exception{
		return dictionaryService.update(dictionary);
	}
	
	@PostMapping("delete")
	public ResponseResult delete(String id) throws Exception{
		return dictionaryService.delete(id);
	}
	
}
