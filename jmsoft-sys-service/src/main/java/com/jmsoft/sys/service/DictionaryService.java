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

	/**
	 * 检查值是否存在
	 * @param value 值
	 * @param parentToken 父级编码
	 * @return
	 */
	public boolean checkValueAndParentToken(String value, String parentToken);

	/**
	 * 根据父级字典token获取字典集合
	 * @param parentToken 父token
	 * @return
	 */
	public ResponseResult findByParentToken(String parentToken);

}
