package com.jmsoft.sys.service;

import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.sys.vo.FileVo;

/**
 * 系统公共服务
 * @author Jack
 *
 */
public interface CommonService {

	/**
	 * 参数加密
	 * @param value 明文
	 * @return
	 */
	public ResponseResult encrypt(String value) ;
	
	/**
	 * 参数解密
	 * @param value 密文
	 * @return
	 */
	public ResponseResult decrypt(String value);

	/**
	 * 图片上传
	 * @param fileVo
	 * @return
	 * @throws Exception
	 */
	public ResponseResult uploadImg(FileVo fileVo) throws Exception;

	/**
	 * 文件上传
	 * @param fileVo
	 * @return
	 * @throws Exception
	 */
	public ResponseResult uploadFile(FileVo fileVo) throws Exception;

	/**
	 * 获取地址信息
	 * @param parentCode
	 * @return
	 */
	public ResponseResult findAddress(String parentCode);

}
