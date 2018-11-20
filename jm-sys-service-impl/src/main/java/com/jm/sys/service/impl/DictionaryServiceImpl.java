package com.jm.sys.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.entity.Dictionary;
import com.jm.sys.exception.BizException;
import com.jm.sys.exception.NoException;
import com.jm.sys.exception.ParamException;
import com.jm.sys.repository.DictionaryRepository;
import com.jm.sys.service.DictionaryService;
import com.jm.sys.utils.AnnotationUtils;
import com.jm.sys.utils.Tools;

@Service
@Transactional(rollbackFor=Exception.class)
public class DictionaryServiceImpl extends BaseServiceImpl implements DictionaryService{

	@Resource
	DictionaryRepository dictionaryRepository;

	public ResponseResult findDictionaries() {
		List<Dictionary> dictionaries = dictionaryRepository.findByStatusOrderBySortAsc(0);
		List<Dictionary> list = initDictionary(dictionaries, "0");
		return ResponseResult.SUCCESS(list);
	}

	public ResponseResult save(Dictionary dictionary) throws Exception {
		dictionary.setId(null);
		validateInfo(dictionary);
		dictionary.setStatus(0);
		dictionaryRepository.save(dictionary);
		return ResponseResult.SUCCESS();
	}

	public ResponseResult update(Dictionary dictionary) throws Exception {
		if(dictionary.getId() == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		if(dictionaryRepository.countByIdAndStatus(dictionary.getId(),0)==0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		validateInfo(dictionary);
		
		dictionary.setStatus(0);
		dictionaryRepository.save(dictionary);
		return ResponseResult.SUCCESS();
	}

	private void validateInfo(Dictionary dictionary) throws BizException, ParamException, NoException{

		AnnotationUtils.validateEdit(dictionary);

		if(dictionary.getId() == null){
			if(dictionaryRepository.countByTokenAndStatus(dictionary.getToken().trim(),0) > 0)
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "token已存在").throwBizException();
		}else{
			Dictionary temp = dictionaryRepository.findTop1ByTokenAndStatus(dictionary.getToken().trim(),0);
			if(temp != null && !temp.getId().equals(dictionary.getId()))
				ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "token已存在").throwBizException();
		}

	}

	public ResponseResult delete(String id) {
		if(!Tools.isLong(id))
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "字典信息不存在");

		Dictionary dictionary = dictionaryRepository.findTop1ByIdAndStatus(Long.parseLong(id),0);
		if(dictionary == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "字典信息不存在");

		dictionary.setStatus(1);
		dictionaryRepository.save(dictionary);
		return ResponseResult.SUCCESS();
	}



}
