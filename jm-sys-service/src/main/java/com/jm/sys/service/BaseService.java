package com.jm.sys.service;

import java.util.List;

import com.jm.sys.data.SerialNumberNoConfig;
import com.jm.sys.entity.Dictionary;

public interface BaseService {


	/**
	 * 生成加密基数
	 * @param len 基数长度
	 * @return 结果字符串
	 */
	public String generateEncry(int len);
	
	/**
	 * 生成编号
	 * @return
	 */
	public String generateNo(SerialNumberNoConfig config);
	
	/**
	 * 处理字典信息 
	 * @param dictionaries
	 * @param parentToken
	 * @return
	 */
	public List<Dictionary> initDictionary(List<Dictionary> dictionaries,String parentToken);
	
}
