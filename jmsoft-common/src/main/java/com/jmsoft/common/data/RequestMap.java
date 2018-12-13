package com.jmsoft.common.data;

import java.math.BigDecimal;
import java.util.HashMap;

import com.jmsoft.common.utils.Tools;

/**
 * request请求参数
 * @author Jack
 *
 */
public class RequestMap extends HashMap<String, Object>{

	private static final long serialVersionUID = 3096322287467706288L;

	/**
	 * 获取整型
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key){
		if(key == null || Tools.isEmpty(key) || !this.containsKey(key))
			return null;
		Object value = this.get(key);
		if(!Tools.isInteger(value)) return null;
		return Integer.parseInt(value.toString());
	}

	/**
	 * 获取double
	 * @param key
	 * @return
	 */
	public Double getDouble(String key){
		if(key == null || Tools.isEmpty(key) || !this.containsKey(key))
			return null;
		Object value = this.get(key);
		if(!Tools.isDouble(value)) return null;
		return Double.parseDouble(value.toString());
	}

	/**
	 * 获取BigDecimal
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key){
		if(key == null || Tools.isEmpty(key) || !this.containsKey(key) || this.get(key) == null)
			return null;
		Object value = this.get(key);
		if(!Tools.isBigDecimal(value.toString())) return null;
		return new BigDecimal(value.toString());
	}

	/**
	 * 获取字符串
	 * @param key
	 * @return
	 */
	public String getString(String key){
		if(key == null || Tools.isEmpty(key) || !this.containsKey(key) || this.get(key) == null)
			return null;
		Object value = this.get(key);
		return String.valueOf(value);
	}

	/**
	 * 获取Float
	 * @param key
	 * @return
	 */
	public Float getFloat(String key){
		if(key == null || Tools.isEmpty(key) || !this.containsKey(key) || this.get(key) == null)
			return null;
		Object value = this.get(key);
		if(!Tools.isFloat(value)) return null;
		return Float.parseFloat(value.toString());
	}


}
