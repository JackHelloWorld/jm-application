package com.jmsoft.common.utils;

import org.springframework.beans.BeanUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanTools {

	public static final <T> T setPropertiesToBean(Object source,Class<T> clazz){
		try {
			T target = clazz.newInstance();
			BeanUtils.copyProperties(source, target);
			return target;
		} catch (InstantiationException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
