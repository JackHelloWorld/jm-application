package com.jm.sys.service;

import com.jm.sys.data.ResponseResult;
import com.jm.sys.entity.Dictionary;

/**
 * 字典服务
 * @author Jack
 *
 */
public interface DictionaryService {

	/**
	 * 获取所有字典信息
	 * @return
	 */
	public ResponseResult findDictionaries();

	/**
	 * 保存字典信息
	 * @param dictionary 字典信息
	 * @return
	 */
	public ResponseResult save(Dictionary dictionary) throws Exception;

	/**
	 * 修改字典信息
	 * @param dictionary 字典信息
	 * @return
	 */
	public ResponseResult update(Dictionary dictionary) throws Exception;

	/**
	 * 删除字典信息
	 * @param dictionary 字典信息
	 * @return
	 */
	public ResponseResult delete(String id) throws Exception;

}
