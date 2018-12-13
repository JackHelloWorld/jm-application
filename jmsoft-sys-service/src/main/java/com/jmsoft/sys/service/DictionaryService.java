package com.jmsoft.sys.service;

import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.sys.vo.DictionaryVo;

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
	public ResponseResult save(DictionaryVo dictionary) throws Exception;

	/**
	 * 修改字典信息
	 * @param dictionary 字典信息
	 * @return
	 */
	public ResponseResult update(DictionaryVo dictionary) throws Exception;

	/**
	 * 删除字典信息
	 * @param dictionary 字典信息
	 * @return
	 */
	public ResponseResult delete(String id) throws Exception;

}
