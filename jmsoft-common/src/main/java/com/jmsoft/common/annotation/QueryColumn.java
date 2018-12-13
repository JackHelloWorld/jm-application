package com.jmsoft.common.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 查询列名配置
 * @author Jack
 *
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface QueryColumn {

	/**对应查询列名*/
	String value();
	
}
