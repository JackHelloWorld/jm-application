package com.jmsoft.user.vo;

import java.io.Serializable;

import com.jmsoft.common.annotation.ValidateEdit;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录请求参数封装数据
 * @author Jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserLoginVo implements Serializable{
	
	private static final long serialVersionUID = 5225091516850464802L;
	
	/**登录名*/
	@ValidateEdit(message="请输入登录名")
	@ApiParam(value="登录名",required=true)
	private String login_name;
	/**登录密码*/
	@ValidateEdit(message="请输入密码")
	@ApiParam(value="登录面",required=true)
	private String password;
	
	/**客户端ip*/
	@ApiParam(hidden=true)
	@ValidateEdit(message="客户端信息有误")
	private String ip;

}
